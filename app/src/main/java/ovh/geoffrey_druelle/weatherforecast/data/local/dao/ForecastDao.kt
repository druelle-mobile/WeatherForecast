package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.TypeConverters
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.converter.CityConverter
import ovh.geoffrey_druelle.weatherforecast.data.local.converter.ListItemConverter
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity

@TypeConverters(CityConverter::class, ListItemConverter::class)
@Dao
interface ForecastDao : BaseDao<ForecastEntity> {

    @Query("SELECT * FROM ForecastEntity")
    fun getAll() : Single<List<ForecastEntity>>

    @Query("SELECT COUNT(*) FROM ForecastEntity")
    fun countEntries(): Int

    @Query("DELETE FROM ForecastEntity")
    fun deleteAll()
}
