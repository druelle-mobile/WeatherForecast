package ovh.geoffrey_druelle.weatherforecast.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ovh.geoffrey_druelle.weatherforecast.WeatherForecastApplication
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.CityDao
import ovh.geoffrey_druelle.weatherforecast.data.local.database.WeatherForecastDatabase
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CityEntity
import ovh.geoffrey_druelle.weatherforecast.data.repository.CityRepository
import ovh.geoffrey_druelle.weatherforecast.utils.getOrAwaitValue

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, application = WeatherForecastApplication::class)
class CityRepositoryTest : CoroutineScope {

    override val coroutineContext
        get() = Dispatchers.Main

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var cityRepository: CityRepository
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

        cityRepository = CityRepository(WeatherForecastApplication.instance)
    }

    @After
    fun after() {
        weatherForecastDatabase.close()
        stopKoin()
    }

    @Test
    fun `insert city, count, delete, count`() {
        runBlocking {
            cityRepository.insert(CityEntity(1, "Paris", "FR", 0.0, 0.0, 123456, 123456, 1))

            val attendeeBeforeDel = 1
            val count = cityRepository.countEntries()

            assertEquals(attendeeBeforeDel, count)

            ////////////////

            cityRepository.deleteAll()

            val attendeeAfterDel = 0
            val countAfterDel = cityRepository.countEntries()

            assertEquals(attendeeAfterDel, countAfterDel)
        }
    }

    @Test
    fun `insert city, get live city from id`() {
        runBlocking {
            withContext(Dispatchers.IO) {
                cityRepository.insert(CityEntity(1, "Paris", "FR", 0.0, 0.0, 123456, 123456, 1))

                val city = cityRepository.getLiveCity(1).getOrAwaitValue()
                val attendee = "Paris"

                assertEquals(attendee, city.name)
            }
        }
    }

    @Test
    fun `insert city, get city from id`() {
        runBlocking {
            cityRepository.insert(CityEntity(1, "Paris", "FR", 0.0, 0.0, 123456, 123456, 1))

            val city = cityRepository.getCity(1)
            val attendee = "Paris"

            assertEquals(attendee, city.name)
        }
    }
}