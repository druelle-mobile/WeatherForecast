package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity

@Dao
interface ForecastDao : BaseDao<ForecastEntity> {

    @Query("SELECT * FROM ForecastEntity")
    fun getAll() : Single<List<ForecastEntity>>

    @Query("SELECT COUNT(*) FROM ForecastEntity")
    fun countEntries(): Int
}
