package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CloudsEntity

@Dao
interface CloudsDao : BaseDao<CloudsEntity> {

    @Query("SELECT * FROM CloudsEntity")
    fun getAll() : Single<List<CloudsEntity>>

    @Query("SELECT COUNT(*) FROM CloudsEntity")
    fun countEntries(): Int
}
