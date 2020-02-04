package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.SysEntity

@Dao
interface SysDao : BaseDao<SysEntity> {

    @Query("SELECT * FROM SysEntity")
    fun getAll() : Single<List<SysEntity>>

    @Query("SELECT COUNT(*) FROM SysEntity")
    fun countEntries(): Int
}
