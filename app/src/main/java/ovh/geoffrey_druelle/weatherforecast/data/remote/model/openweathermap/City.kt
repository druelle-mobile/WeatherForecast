package ovh.geoffrey_druelle.weatherforecast.data.remote.model.openweathermap

import  com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("coord") val coord: Coord,
    @SerializedName("country") val country: String,
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("population") val population: Int,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("timezone") val timezone: Int
)