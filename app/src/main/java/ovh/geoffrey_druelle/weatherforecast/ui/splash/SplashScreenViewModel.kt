package ovh.geoffrey_druelle.weatherforecast.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import kotlinx.coroutines.*
import ovh.geoffrey_druelle.weatherforecast.R
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.appContext
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.instance
import ovh.geoffrey_druelle.weatherforecast.core.BaseViewModel
import ovh.geoffrey_druelle.weatherforecast.data.remote.api.CitiesListApi
import ovh.geoffrey_druelle.weatherforecast.data.remote.api.OpenWeatherMapApi
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.cities.CitiesListItem
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.openweathermap.Forecast
import ovh.geoffrey_druelle.weatherforecast.data.repository.CitiesListItemRepository
import ovh.geoffrey_druelle.weatherforecast.data.repository.CityRepository
import ovh.geoffrey_druelle.weatherforecast.data.repository.ForecastRepository
import ovh.geoffrey_druelle.weatherforecast.utils.cleanForecastDatabase
import ovh.geoffrey_druelle.weatherforecast.utils.createCitiesListObject
import ovh.geoffrey_druelle.weatherforecast.utils.createCityObject
import ovh.geoffrey_druelle.weatherforecast.utils.createForecastObject
import ovh.geoffrey_druelle.weatherforecast.utils.helper.ConnectivityHelper.isConnectedToNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class SplashScreenViewModel(
    private val owmApi: OpenWeatherMapApi,
    private val cityApi: CitiesListApi
) : BaseViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var job: Job = Job()

    private var forecastRepository = ForecastRepository(instance)
    private var cityRepository = CityRepository(instance)
    private var citiesListRepository = CitiesListItemRepository(instance)

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected

    private val _noDataNoConnection = MutableLiveData<Boolean>()
    val noDataNoConnection: LiveData<Boolean>
        get() = _noDataNoConnection

    private val _navToHome = MutableLiveData<Boolean>()
    val navToHome: LiveData<Boolean>
        get() = _navToHome

    var appVersion: String = instance.getVersionName()

    init {
        testConnection()
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    private fun testConnection() {
        val bool: Boolean = if (isConnectedToNetwork(appContext)) {
            _isConnected.postValue(true)
            true
        } else {
            _isConnected.postValue(false)
            false
        }

        checkCitiesListDatas(bool)
    }

    private fun checkCitiesListDatas(bool: Boolean) {
        val citiesListCount = runBlocking {
            citiesListRepository.getLength()
        }

        when {
            bool && citiesListCount != 0 -> checkForecastDatas(bool)
            bool && citiesListCount == 0 -> {
                launchRequestForCitiesList(bool)
            }
            else -> _noDataNoConnection.postValue(true)
        }
    }

    private fun checkForecastDatas(bool: Boolean) {
        val forecastCount = runBlocking {
            forecastRepository.countForecastEntries()
        }

        when {
            bool -> launchRequestForForecastDatas()
            forecastCount != 0 -> _navToHome.postValue(true)
            else -> _noDataNoConnection.postValue(true)
        }
    }

    private fun launchRequestForCitiesList(bool: Boolean) {
        val call: Call<JsonArray> = cityApi.readJson()
        call.enqueue(object : Callback<JsonArray> {
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Timber.d(String.format("launchRequestForCitiesList : Failure on call - %s", t))
                Timber.d(
                    String.format(
                        "launchRequestForCitiesList : Failure on call - %s",
                        t.message
                    )
                )
            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val jsonArray = response.body() as JsonArray
                if (response.isSuccessful) {
                    getCitiesListFromJsonArray(jsonArray, bool)
                } else {
                    Timber.d(String.format("launchRequestForCitiesList: got response but not successful"))
                }
            }
        })
    }

    private fun getCitiesListFromJsonArray(
        jsonArray: JsonArray,
        bool: Boolean
    ) {
        runBlocking {
            withContext(Dispatchers.IO) {
                val gsonBuilder = GsonBuilder().serializeNulls()
                val gson = gsonBuilder.create()
                val citiesListItem: List<CitiesListItem> =
                    gson.fromJson(jsonArray, Array<CitiesListItem>::class.java).toList()
                populateCitiesListDatabase(citiesListItem, bool)
            }
        }
    }

    private fun populateCitiesListDatabase(
        citiesListItem: List<CitiesListItem>,
        bool: Boolean
    ) {
        runBlocking {
            for (i in citiesListItem.indices) {
                citiesListRepository.insert(createCitiesListObject(citiesListItem[i]))
            }
            citiesListRepository.deleteContinents()
            checkForecastDatas(bool)
        }
    }

    private fun launchRequestForForecastDatas() {
        val call: Call<Forecast> = owmApi.getDatasFromCityName(appContext.getString(R.string.paris), appContext.getString(R.string.metric_unit))
        Timber.i("Call : %s", call.toString())
        call.enqueue(object : Callback<Forecast> {
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Timber.d(String.format("launchRequestForForecastDatas : Failure on call - %s", t))
                Timber.d(
                    String.format(
                        "launchRequestForForecastDatas : Failure on call - %s",
                        t.message
                    )
                )
            }

            override fun onResponse(
                call: Call<Forecast>,
                response: Response<Forecast>
            ) {
                Timber.i("Response : %s", response.message().toString())
                Timber.i("Response : %s", response.body().toString())
                if (response.isSuccessful) {
                    val forecast: Forecast = response.body()!!
                    cleanForecastDatabase()
                    populateForecastDatabase(forecast)
                    _noDataNoConnection.postValue(false)
                } else {
                    Timber.d(String.format("launchRequestForDatas: got response but not successful"))
                }
            }
        })
    }

    private fun populateForecastDatabase(forecast: Forecast) {
        runBlocking {
            cityRepository.insert(createCityObject(forecast))
            for (i in forecast.list.indices) {
                forecastRepository.insert(createForecastObject(forecast.city.id, forecast.list[i]))
            }
            _navToHome.postValue(true)
        }
    }
}
