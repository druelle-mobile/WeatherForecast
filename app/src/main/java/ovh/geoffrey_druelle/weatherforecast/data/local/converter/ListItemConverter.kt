package ovh.geoffrey_druelle.weatherforecast.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ListItemEntity
import java.lang.reflect.Type

class ListItemConverter {

    @TypeConverter
    fun listToString(list: List<ListItemEntity>?): String? = Gson().toJson(list)

    @TypeConverter
    fun stringToList(string: String?): List<ListItemEntity>? {
        val type: Type = object : TypeToken<List<ListItemEntity?>?>() {}.type
        return Gson().fromJson(string, type)
    }
}