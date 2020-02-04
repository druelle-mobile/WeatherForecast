package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.WeatherEntity

@Dao
interface WeatherDao : BaseDao<WeatherEntity> {

    @Query("SELECT * FROM WeatherEntity")
    fun getAll() : Single<List<WeatherEntity>>

    @Query("SELECT COUNT(*) FROM WeatherEntity")
    fun countEntries(): Int
}
