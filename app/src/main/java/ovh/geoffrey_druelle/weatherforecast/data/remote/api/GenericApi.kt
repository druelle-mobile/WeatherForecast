package ovh.geoffrey_druelle.weatherforecast.data.remote.api

import ovh.geoffrey_druelle.weatherforecast.data.remote.model.GenericApiModel
import retrofit2.Call
import retrofit2.http.GET

interface GenericApi {

//    @GET("?dataset=$DATASET&rows=10")
    fun getTenRows(): Call<GenericApiModel>
}
