package ovh.geoffrey_druelle.weatherforecast.data.remote.model

import com.google.gson.annotations.SerializedName

data class GenericApiModel(
    @SerializedName("var1") val var1: Int,
    @SerializedName("var2") val var2: String,
    @SerializedName("var3") val var3: Int
) {

}
