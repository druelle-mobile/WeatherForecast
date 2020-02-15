package ovh.geoffrey_druelle.weatherforecast.utils

import kotlinx.coroutines.runBlocking
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CityEntity
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Forecast
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.ListItem
import ovh.geoffrey_druelle.weatherforecast.data.repository.CityRepository
import ovh.geoffrey_druelle.weatherforecast.data.repository.ForecastRepository

private var forecastRepository = ForecastRepository(WeatherForecastApplication.instance)
private var cityRepository = CityRepository(WeatherForecastApplication.instance)

fun cleanForecastDatabase() {
    runBlocking {
        forecastRepository.deleteAll()
        cityRepository.deleteAll()
    }
}

fun populateForecastDatabase(forecast: Forecast) {
    runBlocking {
        cityRepository.insert(createCityObject(forecast))
        for (i in forecast.list.indices) {
            forecastRepository.insert(createForecastObject(forecast.city.id, forecast.list[i]))
        }
    }
}

fun createCityObject(forecast: Forecast): CityEntity {
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

fun createForecastObject(
    cityId: Long,
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
