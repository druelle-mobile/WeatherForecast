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
import ovh.geoffrey_druelle.weatherforecast.data.remote.api.OpenWeatherMapApi
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Forecast
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

    private var repo = ForecastRepository(instance)

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
        if (isConnectedToNetwork(appContext)) _isConnected.postValue(true)
        else _isConnected.postValue(false)
        downloadDatas()
    }

    private fun downloadDatas() {
        val count = runBlocking {
            repo.countForecastEntries()
        }
        when {
            isConnected.value!! -> launchRequestForDatas()
            count != 0 -> _navToHome.postValue(true)
            else -> _noDataNoConnection.postValue(true)
        }
    }

    internal fun launchRequestForDatas() {
        val call: Call<Forecast> = api.getFullDatas("Paris", "metric")
        call.enqueue(object : Callback<Forecast> {
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Timber.d(String.format("launchRequestForDatas : %s", t))
            }

            override fun onResponse(
                call: Call<Forecast>,
                response: Response<Forecast>
            ) {
                if (response.isSuccessful) {
                    succeedRequestForDatas()
                    val forecast: Forecast = response.body()!!
                    populateDatabase(forecast)
                    _navToHome.postValue(true)
                } else {
                    failedRequestForDatas()
                    Timber.d(String.format("launchRequestForDatas: got response but not successful"))
                }
            }
        })
    }

    private fun populateDatabase(forecast: Forecast) {
        runBlocking {
            repo.insertForecast(forecast)
            for (i in forecast.list.indices) {
                repo.insertForecastListItems(forecast.list[i])
            }
        }
    }


    internal fun failedRequestForDatas() {
        _succeedRequestForDatas.postValue(false)
    }

    internal fun succeedRequestForDatas() {
        _succeedRequestForDatas.postValue(true)
    }
}
