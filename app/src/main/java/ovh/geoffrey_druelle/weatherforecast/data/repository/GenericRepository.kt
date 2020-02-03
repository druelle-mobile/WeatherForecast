package ovh.geoffrey_druelle.weatherforecast.data.repository

import android.app.Application
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.GenericDao
import ovh.geoffrey_druelle.weatherforecast.data.local.database.AppDatabase
import ovh.geoffrey_druelle.weatherforecast.data.local.model.GenericModel
import kotlin.coroutines.CoroutineContext

// Still subjective
class GenericRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var genericDao: GenericDao

    init {
        val db = AppDatabase.getInstance(application)
        genericDao = db.genericDao()
    }

    suspend fun countEntriesInTable(): Int {
        return withContext(Dispatchers.IO) {
            genericDao.countEntries()
        }
    }

    suspend fun insert(genericItem: GenericModel) {
        withContext(Dispatchers.IO) {
            genericDao.insert(genericItem)
        }
    }

    suspend fun delete(genericItem: GenericModel) {
        withContext(Dispatchers.IO) {
            genericDao.delete(genericItem)
        }
    }

    fun getGenericList() : Single<List<GenericModel>> {
        return genericDao.getAll()
    }
}