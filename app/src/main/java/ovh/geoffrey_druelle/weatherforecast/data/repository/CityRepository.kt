package ovh.geoffrey_druelle.weatherforecast.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.CityDao
import ovh.geoffrey_druelle.weatherforecast.data.local.database.WeatherForecastDatabase
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CityEntity
import kotlin.coroutines.CoroutineContext

class CityRepository(app: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var cityDao: CityDao

    init {
        val db = WeatherForecastDatabase.getInstance(app)
        cityDao = db.cityDao()
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            cityDao.deleteAll()
        }
    }

    suspend fun insert(city: CityEntity) {
        withContext(Dispatchers.IO) {
            cityDao.insert(city)
        }
    }

    fun getCity(id: Int): LiveData<CityEntity> {
        return cityDao.getCityFromId(id)

    }
}