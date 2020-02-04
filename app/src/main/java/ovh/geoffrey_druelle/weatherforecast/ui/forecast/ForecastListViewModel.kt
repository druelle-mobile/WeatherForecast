package ovh.geoffrey_druelle.weatherforecast.ui.forecast

import ovh.geoffrey_druelle.weatherforecast.core.BaseViewModel
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity

class ForecastListViewModel : BaseViewModel() {
    lateinit var forecastList : List<ForecastEntity>
}
