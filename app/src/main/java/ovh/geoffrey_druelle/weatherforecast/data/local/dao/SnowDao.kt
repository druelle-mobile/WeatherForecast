package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.SnowEntity

@Dao
interface SnowDao : BaseDao<SnowEntity> {

    @Query("SELECT * FROM SnowEntity")
    fun getAll() : Single<List<SnowEntity>>

    @Query("SELECT COUNT(*) FROM SnowEntity")
    fun countEntries(): Int
}
