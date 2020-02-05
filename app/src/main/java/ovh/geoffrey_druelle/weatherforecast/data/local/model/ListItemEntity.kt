package ovh.geoffrey_druelle.weatherforecast.data.local.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ovh.geoffrey_druelle.weatherforecast.data.local.converter.*
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.ListItem

@TypeConverters(
    CloudsConverter::class,
    MainConverter::class,
    RainConverter::class,
    SnowConverter::class,
    SysConverter::class,
    WeatherConverter::class,
    WindConverter::class
)
@Entity
data class ListItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var clouds: CloudsEntity?,
    var dt: Int,
    var dt_txt: String,
    var main: MainEntity?,
    var rain: RainEntity?,
    var snow: SnowEntity?,
    var sys: SysEntity?,
    var weather: WeatherEntity?,
    var wind: WindEntity?
) {
    @Ignore
    constructor() : this(
        id = 0,
        clouds = null,
        dt = 0,
        dt_txt = "",
        main = null,
        rain = null,
        snow = null,
        sys = null,
        weather = null,
        wind = null
    )

    @Ignore
    constructor(listItem: ListItem) : this(
        id = 0,
        clouds = CloudsEntity(listItem.clouds),
        dt = listItem.dt,
        dt_txt = listItem.dt_txt,
        main = MainEntity(listItem.main),
        rain = RainEntity(listItem.rain),
        snow = SnowEntity(listItem.snow),
        sys = SysEntity(listItem.sys),
        weather = WeatherEntity(listItem.weather[0]),
        wind = WindEntity(listItem.wind)
    )
}