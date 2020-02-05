package ovh.geoffrey_druelle.weatherforecast.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CityEntity

class CityConverter {

    @TypeConverter
    fun cityToString(city: CityEntity) : String = Gson().toJson(city)

    @TypeConverter
    fun stringToCity(string: String) : CityEntity = Gson().fromJson(string, CityEntity::class.java)
}