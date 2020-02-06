package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity

@Dao
interface ForecastDao : BaseDao<ForecastEntity> {

    @Query("SELECT * FROM ForecastEntity")
    fun getAll() : LiveData<List<ForecastEntity>>

    @Query("SELECT COUNT(*) FROM ForecastEntity")
    fun countEntries(): Int

    @Query("DELETE FROM ForecastEntity")
    fun deleteAll()
}
