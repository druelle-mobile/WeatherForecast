package ovh.geoffrey_druelle.weatherforecast.data.remote.model.openweathermap

import  com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h") val volumeRainLastThreeHours: Double
)