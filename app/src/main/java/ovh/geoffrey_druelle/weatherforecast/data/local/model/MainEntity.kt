package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Main

@Entity
data class MainEntity(
    var feelsLike: Double,
    var grndLevel: Int,
    var humidity: Int,
    var pressure: Int,
    var seaLevel: Int,
    var temp: Double,
    var tempKf: Double,
    var tempMax: Double,
    var tempMin: Double
) {

    @Ignore
    constructor(main: Main) : this(
        feelsLike = main.feels_like,
        grndLevel = main.grnd_level,
        humidity = main.humidity,
        pressure = main.pressure,
        seaLevel = main.sea_level,
        temp = main.temp,
        tempKf = main.temp_kf,
        tempMax = main.temp_max,
        tempMin = main.temp_min
    )
}