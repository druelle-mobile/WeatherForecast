package ovh.geoffrey_druelle.weatherforecast.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import ovh.geoffrey_druelle.weatherforecast.data.local.model.SysEntity

class SysConverter {
    @TypeConverter
    fun sysToString(sys: SysEntity) : String = Gson().toJson(sys)

    @TypeConverter
    fun stringToSys(string: String) : SysEntity = Gson().fromJson(string, SysEntity::class.java)
}