package ovh.geoffrey_druelle.weatherforecast.data.remote.model

import  com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("`3h`") val `3h`: Double
)