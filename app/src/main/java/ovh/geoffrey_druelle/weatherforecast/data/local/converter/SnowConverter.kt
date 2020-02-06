package ovh.geoffrey_druelle.weatherforecast.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import ovh.geoffrey_druelle.weatherforecast.data.local.model.SnowEntity

class SnowConverter {
    @TypeConverter
    fun snowToString(snow: SnowEntity?) : String? = Gson().toJson(snow)

    @TypeConverter
    fun stringToSnow(string: String?) : SnowEntity? = Gson().fromJson(string, SnowEntity::class.java)
}