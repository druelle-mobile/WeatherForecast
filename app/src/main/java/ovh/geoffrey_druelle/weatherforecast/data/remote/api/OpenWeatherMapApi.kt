package ovh.geoffrey_druelle.weatherforecast.data.remote.api

import ovh.geoffrey_druelle.weatherforecast.data.remote.model.openweathermap.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {

    @GET("forecast")
    fun getDatasFromCityName(
        @Query("q") city: String,
        @Query("units") units: String
    ): Call<Forecast>

    @GET("forecast")
    fun getDatasFromCityId(
        @Query("id") cityId: Long,
        @Query("units") units: String
    ): Call<Forecast>
}
