package ovh.geoffrey_druelle.weatherforecast.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.CitiesListItemDao
import ovh.geoffrey_druelle.weatherforecast.data.local.database.WeatherForecastDatabase
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CitiesListItemEntity
import kotlin.coroutines.CoroutineContext

class CitiesListItemRepository(app: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var citiesListItemDao: CitiesListItemDao

    init {
        val db = WeatherForecastDatabase.getInstance(app)
        citiesListItemDao = db.citiesListItemDao()
    }

    suspend fun insert(citiesListItem: CitiesListItemEntity) {
        withContext(Dispatchers.IO) {
            citiesListItemDao.insert(citiesListItem)
        }
    }

    suspend fun getLength(): Int {
        return withContext(Dispatchers.IO) {
            citiesListItemDao.getLength()
        }
    }

    fun getCitiesFromName(name: String): LiveData<List<CitiesListItemEntity>> {
        return citiesListItemDao.findCitiesFromName("%$name%")
    }

    fun deleteContinents() {
        runBlocking {
            citiesListItemDao.deleteAsia()
            citiesListItemDao.deleteEurope()
            citiesListItemDao.deleteNorthAmerica()
            citiesListItemDao.deleteSouthAmerica()
            citiesListItemDao.deleteEarth()
            citiesListItemDao.deleteAfrica()
            citiesListItemDao.deleteOceania()
            citiesListItemDao.deleteAntartica()
        }
    }

    suspend fun getCityIdFromNameAndCountry(city: String, country: String): Long {
        return withContext(Dispatchers.IO) {
            citiesListItemDao.getCityIdFromNameAndCountry(city, country)
        }
    }
}