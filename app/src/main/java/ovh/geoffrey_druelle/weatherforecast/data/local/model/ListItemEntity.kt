package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity

@Entity
data class ListItemEntity(
    var cloudsEntity: CloudsEntity,
    var dt: Int,
    var dt_txt: String,
    var main: MainEntity,
    var rain: RainEntity,
    var sys: SysEntity,
    var weather: List<WeatherEntity>,
    var wind: WindEntity
)