package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CoordEntity

@Dao
interface CoordDao : BaseDao<CoordEntity> {

    @Query("SELECT * FROM CoordEntity")
    fun getAll() : Single<List<CoordEntity>>

    @Query("SELECT COUNT(*) FROM CoordEntity")
    fun countEntries(): Int
}
