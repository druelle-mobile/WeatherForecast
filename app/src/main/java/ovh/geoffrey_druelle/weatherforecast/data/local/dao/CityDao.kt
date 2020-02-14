package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CityEntity

@Dao
interface CityDao : BaseDao<CityEntity> {

    @Query("SELECT COUNT(*) FROM CityEntity")
    fun countEntries(): Int

    @Query("DELETE FROM CityEntity")
    fun deleteAll()

    @Query("SELECT * FROM CityEntity WHERE id = :id")
    fun getLiveCityFromId(id: Long): LiveData<CityEntity>

    @Query("SELECT * FROM CityEntity WHERE id = :id")
    fun getCityFromId(id: Long): CityEntity
}
