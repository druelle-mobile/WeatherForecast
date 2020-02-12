package ovh.geoffrey_druelle.weatherforecast.data.remote.model.cities

import com.google.gson.annotations.SerializedName

data class CitiesCoord(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)