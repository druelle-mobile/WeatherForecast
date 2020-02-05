package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Weather

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var description: String,
    var icon: String,
    var main: String
) {

    @Ignore
    constructor(weather: Weather) : this(
        description = weather.description,
        icon = weather.icon,
        id = weather.id,
        main = weather.main
    )
}