package ovh.geoffrey_druelle.weatherforecast.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CloudsEntity

class CloudsConverter {

    @TypeConverter
    fun cloudsToString(clouds: CloudsEntity) : String = Gson().toJson(clouds)

    @TypeConverter
    fun stringToClouds(string: String) : CloudsEntity = Gson().fromJson(string, CloudsEntity::class.java)
}