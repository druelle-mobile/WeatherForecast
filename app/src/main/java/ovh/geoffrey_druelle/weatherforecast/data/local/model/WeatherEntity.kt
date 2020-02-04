package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity

@Entity
data class WeatherEntity(
    var description: String,
    var icon: String,
    var id: Int,
    var main: String
)