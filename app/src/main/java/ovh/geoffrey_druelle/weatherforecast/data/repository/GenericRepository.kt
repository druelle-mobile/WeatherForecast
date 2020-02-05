package ovh.geoffrey_druelle.weatherforecast.data.repository

import android.app.Application
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.CityDao
import ovh.geoffrey_druelle.weatherforecast.data.local.database.WeatherForecastDatabase
import kotlin.coroutines.CoroutineContext

// Still subjective
class GenericRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var cityDao: CityDao

    init {
        val db = WeatherForecastDatabase.getInstance(application)
        cityDao = db.cityDao()
    }

    suspend fun countEntriesInTable(): Int {
        return withContext(Dispatchers.IO) {
            cityDao.countEntries()
        }
    }

//    suspend fun insert(genericItem: GenericModel) {
//        withContext(Dispatchers.IO) {
//            cityDao.insert(genericItem)
//        }
//    }
//
//    suspend fun delete(genericItem: GenericModel) {
//        withContext(Dispatchers.IO) {
//            cityDao.delete(genericItem)
//        }
//    }
//
//    fun getGenericList() : Single<List<GenericModel>> {
//        return cityDao.getAll()
//    }
}