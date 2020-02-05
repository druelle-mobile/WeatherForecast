package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Wind

@Entity
data class WindEntity(
    var deg: Int,
    var speed: Double
) {

    @Ignore
    constructor(wind: Wind) : this(
        deg = wind.deg,
        speed = wind.speed
    )
}