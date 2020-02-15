package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class CitiesListItemEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Long,
    var name: String,
    var country: String
)