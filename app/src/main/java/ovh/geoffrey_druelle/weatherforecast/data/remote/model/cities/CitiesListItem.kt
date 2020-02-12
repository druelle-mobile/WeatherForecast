package ovh.geoffrey_druelle.weatherforecast.data.remote.model.cities

import com.google.gson.annotations.SerializedName

data class CitiesListItem(
    @SerializedName("coord") val coord: CitiesCoord,
    @SerializedName("country") val country: String,
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
)