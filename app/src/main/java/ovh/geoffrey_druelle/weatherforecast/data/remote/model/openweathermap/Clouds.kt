package ovh.geoffrey_druelle.weatherforecast.data.remote.model.openweathermap

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all") val all: Int
)