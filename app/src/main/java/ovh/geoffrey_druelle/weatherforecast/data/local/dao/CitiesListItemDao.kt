package ovh.geoffrey_druelle.weatherforecast.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ovh.geoffrey_druelle.weatherforecast.core.BaseDao
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CitiesListItemEntity

@Dao
interface CitiesListItemDao : BaseDao<CitiesListItemEntity> {

    @Query("SELECT COUNT(*) FROM CitiesListItemEntity")
    fun getLength(): Int

    @Query("SELECT * FROM CitiesListItemEntity WHERE name LIKE :name ORDER BY country ASC")
    fun findCitiesFromName(name: String): LiveData<List<CitiesListItemEntity>>

    @Query("DELETE FROM CitiesListItemEntity WHERE id = 6255147")
    fun deleteAsia()
    @Query("DELETE FROM CitiesListItemEntity WHERE id = 6255148")
    fun deleteEurope()
    @Query("DELETE FROM CitiesListItemEntity WHERE id = 6255149")
    fun deleteNorthAmerica()
    @Query("DELETE FROM CitiesListItemEntity WHERE id = 6255150")
    fun deleteSouthAmerica()
    @Query("DELETE FROM CitiesListItemEntity WHERE id = 6295630")
    fun deleteEarth()
    @Query("DELETE FROM CitiesListItemEntity WHERE id = 6255146")
    fun deleteAfrica()
    @Query("DELETE FROM CitiesListItemEntity WHERE id = 6255151")
    fun deleteOceania()
    @Query("DELETE FROM CitiesListItemEntity WHERE id = 6255152")
    fun deleteAntartica()

    @Query("SELECT id FROM CitiesListItemEntity WHERE name = :city AND country = :country")
    fun getCityIdFromNameAndCountry(city: String, country: String): Long
}
