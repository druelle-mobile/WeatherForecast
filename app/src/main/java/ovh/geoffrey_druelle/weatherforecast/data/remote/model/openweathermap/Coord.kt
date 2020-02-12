package ovh.geoffrey_druelle.weatherforecast.data.remote.model.openweathermap

import  com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)