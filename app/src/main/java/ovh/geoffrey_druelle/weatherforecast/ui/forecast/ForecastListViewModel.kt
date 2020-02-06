package ovh.geoffrey_druelle.weatherforecast.ui.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication.Companion.instance
import ovh.geoffrey_druelle.weatherforecast.core.BaseViewModel
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CityEntity
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity
import ovh.geoffrey_druelle.weatherforecast.data.repository.CityRepository
import ovh.geoffrey_druelle.weatherforecast.data.repository.ForecastRepository
import kotlin.coroutines.CoroutineContext

class ForecastListViewModel : BaseViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var job: Job = Job()

    private var forecastRepository = ForecastRepository(instance)
    private var cityRepository = CityRepository(instance)

    val forecastList: LiveData<List<ForecastEntity>> = forecastRepository.getForecastList()

//    val city: LiveData<CityEntity> = cityRepository.getCity(forecastList.value!![0].cityId)

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
