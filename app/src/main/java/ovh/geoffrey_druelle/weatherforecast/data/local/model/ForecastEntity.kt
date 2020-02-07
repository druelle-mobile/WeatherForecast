package ovh.geoffrey_druelle.weatherforecast.data.local.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["id"],
            childColumns = ["cityId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var cityId: Int,
    var dt: Long,
    var dt_txt: String,
    var cloudsAll: Int,
    var feelsLike: Double,
    var grndLevel: Int,
    var humidity: Int,
    var pressure: Int,
    var seaLevel: Int,
    var temp: Double,
    var tempKf: Double,
    var tempMax: Double,
    var tempMin: Double,
    var volumeRainLastThreeHours: Double?,
    var volumeSnowLastThreeHours: Double?,
    var pod: String,
    var weatherId: Int,
    var weatherIcon: String,
    var degWind: Int,
    var speedWind: Double
) : Parcelable {

    @Ignore
    constructor() : this(
        id = 0,
        cityId = 0,
        dt = 0,
        dt_txt = "",
        cloudsAll = 0,
        feelsLike = 0.0,
        grndLevel = 0,
        humidity = 0,
        pressure = 0,
        seaLevel = 0,
        temp = 0.0,
        tempKf = 0.0,
        tempMax = 0.0,
        tempMin = 0.0,
        volumeRainLastThreeHours = 0.0,
        volumeSnowLastThreeHours = 0.0,
        pod = "",
        weatherId = 0,
        weatherIcon = "",
        degWind = 0,
        speedWind = 0.0
    )
}