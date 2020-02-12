package ovh.geoffrey_druelle.weatherforecast.data.remote.model.openweathermap

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("city") val city: City,
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("cod") val cod: String,
    @SerializedName("list") val list: List<ListItem>,
    @SerializedName("message") val message: Int
)