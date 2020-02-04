package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity

@Entity
data class CoordEntity(
    val lat: Double,
    val lon: Double
)