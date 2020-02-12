package ovh.geoffrey_druelle.weatherforecast.data.remote.api

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET

interface CitiesListApi {

    @GET("city.list.json")
    fun readJson(): Call<JsonArray>
}