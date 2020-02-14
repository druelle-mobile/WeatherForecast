package ovh.geoffrey_druelle.weatherforecast.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.CityDao
import ovh.geoffrey_druelle.weatherforecast.data.local.database.WeatherForecastDatabase
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CityEntity
import ovh.geoffrey_druelle.weatherforecast.utils.getOrAwaitValue

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, application = WeatherForecastApplication::class)
class CityDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherForecastDatabase: WeatherForecastDatabase
    private lateinit var cityDao: CityDao

    @Before
    fun setUp() {
        weatherForecastDatabase = Room.inMemoryDatabaseBuilder(
            WeatherForecastApplication.appContext,
            WeatherForecastDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        cityDao = weatherForecastDatabase.cityDao()
    }

    @After
    fun after() {
        weatherForecastDatabase.close()
        stopKoin()
    }

    @Test
    fun `insert city and count`() {
        cityDao.insert(CityEntity(1,"Paris","FR",0.0,0.0,123456,123456,1))

        val count = cityDao.countEntries()
        val attendee = 1

        assertEquals(attendee, count)
    }

    @Test
    fun `insert city, delete all, count`() {
        cityDao.insert(CityEntity(1,"Paris","FR",0.0,0.0,123456,123456,1))
        cityDao.deleteAll()

        val count = cityDao.countEntries()
        val attendee = 0

        assertEquals(attendee, count)
    }

    @Test
    fun `insert cities and get city from id with livedata`() {
        cityDao.insert(CityEntity(1,"Paris","FR",0.0,0.0,123456,123456,1))
        cityDao.insert(CityEntity(2,"London","GB",0.0,0.0,123456,123456,1))
        cityDao.insert(CityEntity(3,"Washington","US",0.0,0.0,123456,123456,1))

        val city = cityDao.getLiveCityFromId(2).getOrAwaitValue()
        val attendeeCityName = "London"

        assertEquals(attendeeCityName, city.name)
    }

    @Test
    fun `insert cities and get city from id`() {
        cityDao.insert(CityEntity(1,"Paris","FR",0.0,0.0,123456,123456,1))
        cityDao.insert(CityEntity(2,"London","GB",0.0,0.0,123456,123456,1))
        cityDao.insert(CityEntity(3,"Washington","US",0.0,0.0,123456,123456,1))

        val city = cityDao.getCityFromId(2)
        val attendeeCityName = "London"

        assertEquals(attendeeCityName, city.name)
    }
}