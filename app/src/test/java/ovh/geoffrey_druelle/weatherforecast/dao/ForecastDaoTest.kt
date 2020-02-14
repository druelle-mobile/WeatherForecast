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
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.ForecastDao
import ovh.geoffrey_druelle.weatherforecast.data.local.database.WeatherForecastDatabase
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CityEntity
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, application = WeatherForecastApplication::class)
class ForecastDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherForecastDatabase: WeatherForecastDatabase
    private lateinit var forecastDao: ForecastDao
    private lateinit var cityDao: CityDao

    @Before
    fun setUp() {
        weatherForecastDatabase = Room.inMemoryDatabaseBuilder(
            WeatherForecastApplication.appContext,
            WeatherForecastDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        forecastDao = weatherForecastDatabase.forecastDao()
        cityDao = weatherForecastDatabase.cityDao()
    }

    @After
    fun after() {
        weatherForecastDatabase.close()
        stopKoin()
    }

    @Test
    fun `insert a city for test`() {
        cityDao.insert(CityEntity(12345,"Paris","FR",0.0,0.0,123456,123456,1))
    }

    @Test
    fun `insert forecasts and count`() {
        `insert a city for test`()

        forecastDao.insert(ForecastEntity(1,12345,0,"azerty",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))
        forecastDao.insert(ForecastEntity(2,12345,0,"qsdfgh",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))
        forecastDao.insert(ForecastEntity(3,12345,0,"wxcvbn",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))
        forecastDao.insert(ForecastEntity(4,12345,0,"qwerty",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))

        val count = forecastDao.countEntries()
        val attendee = 4

        assertEquals(attendee, count)
    }

    @Test
    fun `insert forecasts, delete and count`() {
        `insert forecasts and count`()

        forecastDao.deleteAll()

        val countAfterDel = forecastDao.countEntries()
        val attendeeAfterDel = 0

        assertEquals(attendeeAfterDel, countAfterDel)
    }

    @Test
    fun `insert datas and get city ID`() {
        `insert forecasts and count`()

        val cityId = forecastDao.getForecastCityId()
        val attendeeCityId = 12345L

        assertEquals(attendeeCityId, cityId)
    }
}