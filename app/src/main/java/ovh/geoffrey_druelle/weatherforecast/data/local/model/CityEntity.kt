package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Long,
    var name: String,
    var country: String,
    var lat: Double,
    var lon: Double,
    var sunrise: Long,
    var sunset: Long,
    var timezone: Int
) {

    @Ignore
    constructor() : this(
        id = 0,
        name = "",
        country = "",
        lat = 0.0,
        lon = 0.0,
        sunset = 0,
        sunrise = 0,
        timezone = 0
    )
}