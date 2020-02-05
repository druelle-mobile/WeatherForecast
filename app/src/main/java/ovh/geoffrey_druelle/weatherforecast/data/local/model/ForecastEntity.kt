package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ovh.geoffrey_druelle.weatherforecast.data.local.converter.CityConverter
import ovh.geoffrey_druelle.weatherforecast.data.local.converter.ListItemConverter
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Forecast

@TypeConverters(CityConverter::class, ListItemConverter::class)
@Entity
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var city: CityEntity,
    var list: List<ListItemEntity>?
) {

    @Ignore
    constructor(forecast: Forecast) : this(
        id = 0,
        city = CityEntity(forecast.city),
        list = null
    )
}