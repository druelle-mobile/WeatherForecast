package ovh.geoffrey_druelle.weatherforecast.ui.forecast

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import ovh.geoffrey_druelle.weatherforecast.R
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.appContext
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.cityIdPref
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.instance
import ovh.geoffrey_druelle.weatherforecast.core.BaseViewModel
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CityEntity
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity
import ovh.geoffrey_druelle.weatherforecast.data.remote.api.OpenWeatherMapApi
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.openweathermap.Forecast
import ovh.geoffrey_druelle.weatherforecast.data.repository.CityRepository
import ovh.geoffrey_druelle.weatherforecast.data.repository.ForecastRepository
import ovh.geoffrey_druelle.weatherforecast.utils.cleanForecastDatabase
import ovh.geoffrey_druelle.weatherforecast.utils.helper.longLiveData
import ovh.geoffrey_druelle.weatherforecast.utils.populateForecastDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class ForecastListViewModel(private val api: OpenWeatherMapApi) : BaseViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var job: Job = Job()

    private var forecastRepository = ForecastRepository(instance)
    private var cityRepository = CityRepository(instance)

    val forecastList: LiveData<List<ForecastEntity>> = forecastRepository.getForecastList()

    var city: LiveData<CityEntity>? = null

    val liveCityId: LiveData<Long> = cityIdPref.longLiveData("CITY_ID", 2988507)


    fun initCityLiveData() {
        if (forecastList.value!!.isNotEmpty()) {
            city = cityRepository.getLiveCity(forecastList.value!![0].cityId)
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    fun checkForNewForecastRequest(cityId: Long) {
        val forecastCount = runBlocking {
            forecastRepository.countForecastEntries()
        }

        val forecastCityId = runBlocking {
            forecastRepository.getForecastCityId()
        }

        when {
            forecastCityId == liveCityId.value && forecastCount != 0 -> return
            else -> requestNewForecastDatas(cityId)
        }
    }

    private fun requestNewForecastDatas(cityId: Long) {
        val call: Call<Forecast> = api.getDatasFromCityId(cityId, appContext.getString(R.string.metric_unit))
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

            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                Timber.i("Response : %s", response.message().toString())
                Timber.i("Response : %s", response.body().toString())
                if (response.isSuccessful) {
                    val forecast: Forecast = response.body()!!
                    cleanForecastDatabase()
                    populateForecastDatabase(forecast)
                } else {
                    Timber.d(String.format("launchRequestForDatas: got response but not successful"))
                }
            }
        })
    }
}
