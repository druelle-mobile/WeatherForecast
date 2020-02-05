package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.TypeConverters
import io.reactivex.Single
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.converter.*
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ListItemEntity

@TypeConverters(
    CloudsConverter::class,
    MainConverter::class,
    RainConverter::class,
    SnowConverter::class,
    SysConverter::class,
    WeatherConverter::class,
    WindConverter::class
)
@Dao
interface ListItemDao : BaseDao<ListItemEntity> {

    @Query("SELECT * FROM ListItemEntity")
    fun getAll() : Single<List<ListItemEntity>>

    @Query("SELECT COUNT(*) FROM ListItemEntity")
    fun countEntries(): Int

    @Query("DELETE FROM ListItemEntity")
    fun deleteAll()
}
