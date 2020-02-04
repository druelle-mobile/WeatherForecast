package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.RainEntity

@Dao
interface RainDao : BaseDao<RainEntity> {

    @Query("SELECT * FROM RainEntity")
    fun getAll() : Single<List<RainEntity>>

    @Query("SELECT COUNT(*) FROM RainEntity")
    fun countEntries(): Int
}
