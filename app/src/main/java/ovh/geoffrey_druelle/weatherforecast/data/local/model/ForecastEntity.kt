package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var cityEntity: CityEntity,
    var list: List<ListItemEntity>
) {
}