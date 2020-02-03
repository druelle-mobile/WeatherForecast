package ovh.geoffrey_druelle.weatherforecast.data.remote.model

import  com.google.gson.annotations.SerializedName

data class FiveDaysForecastAnswer(
    @SerializedName("city") val city: City,
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("cod") val cod: String,
    @SerializedName("list") val list: List<X>,
    @SerializedName("message") val message: Int
)