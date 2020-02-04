package ovh.geoffrey_druelle.weatherforecast.data.remote.model

import  com.google.gson.annotations.SerializedName

data class Snow(
    @SerializedName("3h") val volumeSnowLastThreeHours: Double
)