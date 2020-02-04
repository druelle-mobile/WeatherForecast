package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity

@Entity
data class MainEntity(
    var feelsLike: Double,
    var grndLevel: Int,
    var humidity: Int,
    var pressure: Int,
    var seaLevel: Int,
    var temp: Double,
    var tempKf: Int,
    var tempMax: Double,
    var tempMin: Double
)