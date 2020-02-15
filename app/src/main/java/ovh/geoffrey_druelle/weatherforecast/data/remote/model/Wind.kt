package ovh.geoffrey_druelle.weatherforecast.data.remote.model

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg") val deg: Int,
    @SerializedName("speed") val speed: Double
)