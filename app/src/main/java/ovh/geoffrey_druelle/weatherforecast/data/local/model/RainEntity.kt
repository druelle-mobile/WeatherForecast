package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Rain

@Entity
data class RainEntity(
    var volumeRainLastThreeHours: Double
) {

    @Ignore
    constructor(rain: Rain) : this(
        volumeRainLastThreeHours = rain.volumeRainLastThreeHours
    )
}