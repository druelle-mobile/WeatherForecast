package ovh.geoffrey_druelle.weatherforecast.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import ovh.geoffrey_druelle.weatherforecast.data.local.model.MainEntity

class MainConverter {

    @TypeConverter
    fun mainToString(main: MainEntity) : String = Gson().toJson(main)

    @TypeConverter
    fun stringToMain(string: String) : MainEntity = Gson().fromJson(string, MainEntity::class.java)
}