package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity

@Entity
data class CityEntity(
    val coordEntity: CoordEntity,
    val country: String,
    val id: Int,
    val name: String,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)