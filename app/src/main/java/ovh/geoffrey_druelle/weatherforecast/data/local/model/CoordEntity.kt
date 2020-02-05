package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Coord

@Entity
data class CoordEntity(
    val lat: Double,
    val lon: Double
) {

    @Ignore
    constructor(coord: Coord) : this(
        lat = coord.lat,
        lon = coord.lon
    )
}