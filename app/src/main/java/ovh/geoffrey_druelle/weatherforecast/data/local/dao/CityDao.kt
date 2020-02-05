package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.TypeConverters
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.converter.CoordConverter
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CityEntity

@TypeConverters(CoordConverter::class)
@Dao
interface CityDao : BaseDao<CityEntity> {

    @Query("SELECT * FROM CityEntity")
    fun getAll() : Single<List<CityEntity>>

    @Query("SELECT COUNT(*) FROM CityEntity")
    fun countEntries(): Int
}
