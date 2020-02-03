package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.GenericModel

@Dao
interface GenericDao : BaseDao<GenericModel> {

    @Query("SELECT * FROM Generic")
    fun getAll() : Single<List<GenericModel>>

    @Query("SELECT COUNT(*) FROM Generic")
    fun countEntries(): Int
}
