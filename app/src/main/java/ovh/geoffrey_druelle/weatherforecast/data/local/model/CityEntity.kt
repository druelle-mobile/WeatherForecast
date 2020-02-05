package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ovh.geoffrey_druelle.weatherforecast.data.local.converter.CoordConverter
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.City

@TypeConverters(CoordConverter::class)
@Entity
data class CityEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var coord: CoordEntity,
    var country: String,
    var name: String,
    var sunrise: Int,
    var sunset: Int,
    var timezone: Int
) {

    @Ignore
    constructor(city: City) : this(
        id = 0,
        coord = CoordEntity(city.coord),
        country = city.country,
        name = city.name,
        sunrise = city.sunrise,
        sunset = city.sunset,
        timezone = city.timezone
    )
}