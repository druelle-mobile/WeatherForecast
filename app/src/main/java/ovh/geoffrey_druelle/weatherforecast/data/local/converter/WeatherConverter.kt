package ovh.geoffrey_druelle.weatherforecast.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import ovh.geoffrey_druelle.weatherforecast.data.local.model.WeatherEntity

class WeatherConverter {
    @TypeConverter
    fun weatherToString(weather: WeatherEntity) : String = Gson().toJson(weather)

    @TypeConverter
    fun stringToWeather(string: String) : WeatherEntity = Gson().fromJson(string, WeatherEntity::class.java)
}