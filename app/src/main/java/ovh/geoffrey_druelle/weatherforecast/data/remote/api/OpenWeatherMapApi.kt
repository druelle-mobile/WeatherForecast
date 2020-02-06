package ovh.geoffrey_druelle.weatherforecast.data.remote.api

import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {

    // Example : http://api.openweathermap.org/data/2.5/forecast?q=Paris&units=metric&appid=428d70aa1268b4be33b6fd9d7f12bc2c

    @GET("forecast")
    fun getFullDatas(
        @Query("q") city: String,
        @Query("units") units: String
    ): Call<Forecast>
}
