package ovh.geoffrey_druelle.weatherforecast.data.remote.model.openweathermap

import  com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod") val pod: String
)