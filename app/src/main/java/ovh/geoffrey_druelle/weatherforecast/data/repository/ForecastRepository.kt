package ovh.geoffrey_druelle.weatherforecast.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.ForecastDao
import ovh.geoffrey_druelle.weatherforecast.data.local.database.WeatherForecastDatabase
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity
import kotlin.coroutines.CoroutineContext

class ForecastRepository(app: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var forecastDao: ForecastDao

    init {
        val db = WeatherForecastDatabase.getInstance(app)
        forecastDao = db.forecastDao()
    }

    suspend fun countForecastEntries(): Int {
        return withContext(Dispatchers.IO) {
            forecastDao.countEntries()
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            forecastDao.deleteAll()
        }
    }

    suspend fun insert(forecastObject: ForecastEntity) {
        withContext(Dispatchers.IO) {
            forecastDao.insert(forecastObject)
        }
    }

    fun getForecastList(): LiveData<List<ForecastEntity>> {
        return forecastDao.getAll()
    }

    suspend fun getForecastCityId(): Long {
        return withContext(Dispatchers.IO) {
            forecastDao.getForecastCityId()
        }
    }
}