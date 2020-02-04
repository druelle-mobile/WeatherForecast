package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.MainEntity

@Dao
interface MainDao : BaseDao<MainEntity> {

    @Query("SELECT * FROM MainEntity")
    fun getAll() : Single<List<MainEntity>>

    @Query("SELECT COUNT(*) FROM MainEntity")
    fun countEntries(): Int
}
