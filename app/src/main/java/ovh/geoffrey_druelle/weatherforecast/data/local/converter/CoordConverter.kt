package ovh.geoffrey_druelle.weatherforecast.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CoordEntity

class CoordConverter {

    @TypeConverter
    fun coordToString(coord: CoordEntity) : String = Gson().toJson(coord)

    @TypeConverter
    fun stringToCoord(string: String) : CoordEntity = Gson().fromJson(string, CoordEntity::class.java)
}
