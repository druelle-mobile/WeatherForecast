package ovh.geoffrey_druelle.weatherforecast.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import ovh.geoffrey_druelle.weatherforecast.data.local.model.WindEntity

class WindConverter {
    @TypeConverter
    fun windToString(wind: WindEntity) : String = Gson().toJson(wind)

    @TypeConverter
    fun stringToWind(string: String) : WindEntity = Gson().fromJson(string, WindEntity::class.java)
}