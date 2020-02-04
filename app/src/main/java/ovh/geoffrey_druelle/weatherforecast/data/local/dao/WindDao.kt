package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.WindEntity

@Dao
interface WindDao : BaseDao<WindEntity> {

    @Query("SELECT * FROM WindEntity")
    fun getAll() : Single<List<WindEntity>>

    @Query("SELECT COUNT(*) FROM WindEntity")
    fun countEntries(): Int
}
