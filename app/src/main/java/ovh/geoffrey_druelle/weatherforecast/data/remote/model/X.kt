package ovh.geoffrey_druelle.weatherforecast.data.remote.model

import com.google.gson.annotations.SerializedName

data class X(
    @SerializedName("clouds") val clouds: Clouds,
    @SerializedName("dt") val dt: Int,
    @SerializedName("dt_txt") val dt_txt: String,
    @SerializedName("main") val main: Main,
    @SerializedName("rain") val rain: Rain,
    @SerializedName("sys") val sys: Sys,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("wind") val wind: Wind
)