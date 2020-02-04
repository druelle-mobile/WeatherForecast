package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity

@Entity
data class WindEntity(
    var deg: Int,
    var speed: Double
)