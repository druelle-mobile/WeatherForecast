package ovh.geoffrey_druelle.weatherforecast.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.appContext
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.instance
import ovh.geoffrey_druelle.weatherforecast.core.BaseViewModel
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CityEntity
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity
import ovh.geoffrey_druelle.weatherforecast.data.remote.api.OpenWeatherMapApi
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Forecast
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.ListItem
import ovh.geoffrey_druelle.weatherforecast.data.repository.CityRepository
import ovh.geoffrey_druelle.weatherforecast.data.repository.ForecastRepository
import ovh.geoffrey_druelle.weatherforecast.utils.helper.ConnectivityHelper.isConnectedToNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class SplashScreenViewModel(private val api: OpenWeatherMapApi) : BaseViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var job: Job = Job()

    private var forecastRepository = ForecastRepository(instance)
    private var cityRepository = CityRepository(instance)

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected

    private val _noDataNoConnection = MutableLiveData<Boolean>()
    val noDataNoConnection: LiveData<Boolean>
        get() = _noDataNoConnection

    private val _isConnectionNeeded = MutableLiveData<Boolean>()
    val isConnectionNeeded: LiveData<Boolean>
        get() = _isConnectionNeeded

    private val _succeedRequestForDatas = MutableLiveData<Boolean>()
    val succeedRequestForDatas: LiveData<Boolean>
        get() = _succeedRequestForDatas

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
        var bool: Boolean
        if (isConnectedToNetwork(appContext)) {
            _isConnected.postValue(true)
            bool = true
        } else {
            _isConnected.postValue(false)
            bool = false
        }

        downloadDatas(bool)
    }

    private fun downloadDatas(bool: Boolean) {
        val count = runBlocking {
            forecastRepository.countForecastEntries()
        }

        when {
            bool -> launchRequestForDatas()
            count != 0 -> _navToHome.postValue(true)
            else -> _noDataNoConnection.postValue(true)
        }
    }

    private fun launchRequestForDatas() {
        val call: Call<Forecast> = api.getFullDatas("Paris", "metric")
        Timber.i("Call : %s", call.toString())
        call.enqueue(object : Callback<Forecast> {
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Timber.d(String.format("launchRequestForDatas : Failure on call - %s", t))
                Timber.d(
                    String.format(
                        "launchRequestForDatas : Failure on call - %s",
                        t.stackTrace
                    )
                )
                Timber.d(String.format("launchRequestForDatas : Failure on call - %s", t.cause))
                Timber.d(String.format("launchRequestForDatas : Failure on call - %s", t.message))
            }

            override fun onResponse(
                call: Call<Forecast>,
                response: Response<Forecast>
            ) {
                Timber.i("Response : %s", response.message().toString())
                Timber.i("Response : %s", response.body().toString())
                if (response.isSuccessful) {
                    succeedRequestForDatas()
                    val forecast: Forecast = response.body()!!
                    cleanDatabase()
                    populateDatabase(forecast)
                    _noDataNoConnection.postValue(false)
                    _navToHome.postValue(true)
                } else {
                    failedRequestForDatas()
                    Timber.d(String.format("launchRequestForDatas: got response but not successful"))
                }
            }
        })
    }

    private fun cleanDatabase() {
        runBlocking {
            forecastRepository.deleteAll()
            cityRepository.deleteAll()
        }
    }

    private fun populateDatabase(forecast: Forecast) {


        runBlocking {
            cityRepository.insert(createCityObject(forecast))
            for (i in forecast.list.indices) {
                forecastRepository.insert(createForecastObject(forecast.city.id, forecast.list[i]))
            }
        }
    }

    private fun createCityObject(forecast: Forecast): CityEntity {
        val city = CityEntity()
        city.id = forecast.city.id
        city.name = forecast.city.name
        city.country = forecast.city.country
        city.lat = forecast.city.coord.lat
        city.lon = forecast.city.coord.lon
        city.sunrise = forecast.city.sunrise
        city.sunset = forecast.city.sunset
        city.timezone = forecast.city.timezone

        return city
    }

    private fun createForecastObject(
        cityId: Int,
        listItem: ListItem
    ): ForecastEntity {
        val item = ForecastEntity()
        item.cityId = cityId
        item.dt = listItem.dt
        item.dt_txt = listItem.dt_txt
        item.cloudsAll = listItem.clouds.all
        item.feelsLike = listItem.main.feels_like
        item.grndLevel = listItem.main.grnd_level
        item.humidity = listItem.main.humidity
        item.pressure = listItem.main.pressure
        item.seaLevel = listItem.main.sea_level
        item.temp = listItem.main.temp
        item.tempKf = listItem.main.temp_kf
        item.tempMax = listItem.main.temp_max
        item.tempMin = listItem.main.temp_min
        item.volumeRainLastThreeHours = listItem.rain?.volumeRainLastThreeHours
        item.volumeSnowLastThreeHours = listItem.snow?.volumeSnowLastThreeHours
        item.pod = listItem.sys.pod
        item.weatherId = listItem.weather[0].id
        item.weatherIcon = listItem.weather[0].icon
        item.degWind = listItem.wind.deg
        item.speedWind = listItem.wind.speed

        return item
    }


    internal fun failedRequestForDatas() {
        _succeedRequestForDatas.postValue(false)
    }

    internal fun succeedRequestForDatas() {
        _succeedRequestForDatas.postValue(true)
    }
}
