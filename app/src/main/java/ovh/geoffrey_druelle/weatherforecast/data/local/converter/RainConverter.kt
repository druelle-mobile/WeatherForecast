package ovh.geoffrey_druelle.weatherforecast.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import ovh.geoffrey_druelle.weatherforecast.data.local.model.RainEntity

class RainConverter {
    @TypeConverter
    fun rainToString(rain: RainEntity) : String = Gson().toJson(rain)

    @TypeConverter
    fun stringToRain(string: String) : RainEntity = Gson().fromJson(string, RainEntity::class.java)
}