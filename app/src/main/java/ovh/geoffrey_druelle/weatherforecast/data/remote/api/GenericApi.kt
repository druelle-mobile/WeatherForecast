package ovh.geoffrey_druelle.weatherforecast.data.remote.api

import retrofit2.Call

interface GenericApi {

//    @GET("?dataset=$DATASET&rows=10")
    fun getTenRows(): Call<GenericApiModel>
}
