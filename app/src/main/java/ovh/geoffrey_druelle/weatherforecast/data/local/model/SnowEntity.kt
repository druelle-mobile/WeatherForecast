package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Snow

@Entity
data class SnowEntity(
    var volumeSnowLastThreeHours: Double
) {

    @Ignore
    constructor(snow: Snow) : this(
        volumeSnowLastThreeHours = snow.volumeSnowLastThreeHours
    )
}