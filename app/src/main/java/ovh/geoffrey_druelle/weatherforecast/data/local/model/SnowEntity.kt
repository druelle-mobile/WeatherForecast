package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity

@Entity
data class SnowEntity(
    var volumeSnowLastThreeHours: Double
)