package ovh.geoffrey_druelle.weatherforecast.data.repository

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.ForecastDao
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.ListItemDao
import ovh.geoffrey_druelle.weatherforecast.data.local.database.WeatherForecastDatabase
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ListItemEntity
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.Forecast
import ovh.geoffrey_druelle.weatherforecast.data.remote.model.ListItem
import kotlin.coroutines.CoroutineContext

class ForecastRepository(app: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var forecastDao: ForecastDao
    private var listItemDao: ListItemDao

    init {
        val db = WeatherForecastDatabase.getInstance(app)
        forecastDao = db.forecastDao()
        listItemDao = db.listItemDao()
    }

    suspend fun countForecastEntries(): Int {
        return withContext(Dispatchers.IO) {
            forecastDao.countEntries()
        }
    }

    suspend fun insertForecast(forecast: Forecast) {
        withContext(Dispatchers.IO) {
            forecastDao.deleteAll()
            forecastDao.insert(ForecastEntity(forecast))
        }
    }

    suspend fun insertForecastListItems(listItem: ListItem) {
        withContext(Dispatchers.IO) {
            listItemDao.deleteAll()
            listItemDao.insert(ListItemEntity(listItem))
        }
    }
}