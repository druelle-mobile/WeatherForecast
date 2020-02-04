package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ListItemEntity

@Dao
interface ListItemDao : BaseDao<ListItemEntity> {

    @Query("SELECT * FROM ListItemEntity")
    fun getAll() : Single<List<ListItemEntity>>

    @Query("SELECT COUNT(*) FROM ListItemEntity")
    fun countEntries(): Int
}
