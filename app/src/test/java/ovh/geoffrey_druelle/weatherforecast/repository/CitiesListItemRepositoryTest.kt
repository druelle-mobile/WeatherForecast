package ovh.geoffrey_druelle.weatherforecast.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.CitiesListItemDao
import ovh.geoffrey_druelle.weatherforecast.data.local.database.WeatherForecastDatabase
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CitiesListItemEntity
import ovh.geoffrey_druelle.weatherforecast.data.repository.CitiesListItemRepository
import ovh.geoffrey_druelle.weatherforecast.utils.getOrAwaitValue
import kotlin.coroutines.CoroutineContext


@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, application = WeatherForecastApplication::class)
class CitiesListItemRepositoryTest : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var citiesListItemRepository: CitiesListItemRepository
    private lateinit var weatherForecastDatabase: WeatherForecastDatabase
    private lateinit var citiesListItemDao: CitiesListItemDao

    @Before
    fun setUp() {
        weatherForecastDatabase = Room.inMemoryDatabaseBuilder(
            WeatherForecastApplication.appContext,
            WeatherForecastDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        citiesListItemDao = weatherForecastDatabase.citiesListItemDao()

        citiesListItemRepository = CitiesListItemRepository(WeatherForecastApplication.instance)
    }

    @After
    fun after() {
        weatherForecastDatabase.close()
        stopKoin()
    }

    @Test
    fun `insert cities in list`() {
        runBlocking {
            citiesListItemRepository.insert(CitiesListItemEntity(1, "Paris", "FR"))
            citiesListItemRepository.insert(CitiesListItemEntity(2, "Tours", "FR"))
            citiesListItemRepository.insert(CitiesListItemEntity(3, "Nantes", "FR"))
            citiesListItemRepository.insert(CitiesListItemEntity(4, "Lyon", "FR"))
            citiesListItemRepository.insert(CitiesListItemEntity(5, "Montpellier", "FR"))
        }
    }

    @Test
    fun `insert cities in list and count`() {
        runBlocking {
            citiesListItemRepository.insert(CitiesListItemEntity(1, "Paris", "FR"))
            citiesListItemRepository.insert(CitiesListItemEntity(2, "Tours", "FR"))
            citiesListItemRepository.insert(CitiesListItemEntity(3, "Nantes", "FR"))
            citiesListItemRepository.insert(CitiesListItemEntity(4, "Lyon", "FR"))
            citiesListItemRepository.insert(CitiesListItemEntity(5, "Montpellier", "FR"))

            val count = citiesListItemRepository.getLength()
            val attendee = 5

            assertEquals(attendee, count)
        }
    }

    @Test
    fun `insert cities in list and get one from name`() {
        runBlocking {
            withContext(Dispatchers.IO) {
                citiesListItemRepository.insert(CitiesListItemEntity(1, "Paris", "FR"))
                citiesListItemRepository.insert(CitiesListItemEntity(2, "Tours", "FR"))
                citiesListItemRepository.insert(CitiesListItemEntity(3, "Nantes", "FR"))
                citiesListItemRepository.insert(CitiesListItemEntity(4, "Lyon", "FR"))
                citiesListItemRepository.insert(CitiesListItemEntity(5, "Montpellier", "FR"))

                val city =
                    citiesListItemRepository.getCitiesFromName("Tours").getOrAwaitValue()

                val attendee = "Tours"

                assertEquals(attendee, city[0].name)
            }
        }
    }

    @Test
    fun `insert cities in list and get ID from name and country`() {
        runBlocking {
            withContext(Dispatchers.IO) {
                citiesListItemRepository.insert(CitiesListItemEntity(1, "Paris", "FR"))
                citiesListItemRepository.insert(CitiesListItemEntity(2, "Tours", "FR"))
                citiesListItemRepository.insert(CitiesListItemEntity(3, "Nantes", "FR"))
                citiesListItemRepository.insert(CitiesListItemEntity(4, "Lyon", "FR"))
                citiesListItemRepository.insert(CitiesListItemEntity(5, "Montpellier", "FR"))

                val cityID =
                    citiesListItemRepository.getCityIdFromNameAndCountry("Tours","FR")

                val attendee = 2L

                assertEquals(attendee, cityID)
            }
        }
    }

    @Test
    fun `insert continents, count, delete, count`() {
        runBlocking {
            withContext(Dispatchers.IO) {
                citiesListItemRepository.insert(CitiesListItemEntity(6255147, "Asia", ""))
                citiesListItemRepository.insert(CitiesListItemEntity(6255148, "Europe", ""))
                citiesListItemRepository.insert(CitiesListItemEntity(6255149, "North America", ""))
                citiesListItemRepository.insert(CitiesListItemEntity(6255150, "South America", ""))
                citiesListItemRepository.insert(CitiesListItemEntity(6295630, "Earth", ""))
                citiesListItemRepository.insert(CitiesListItemEntity(6255146, "Africa", ""))
                citiesListItemRepository.insert(CitiesListItemEntity(6255151, "Oceania", ""))
                citiesListItemRepository.insert(CitiesListItemEntity(6255152, "Antartica", ""))

                val attendeeBeforeDel = 8
                val count = citiesListItemRepository.getLength()

                assertEquals(attendeeBeforeDel, count)

                ////////////////

                citiesListItemRepository.deleteContinents()

                val attendeeAfterDel = 0
                val countAfterDel = citiesListItemRepository.getLength()

                assertEquals(attendeeAfterDel, countAfterDel)
            }
        }
    }
}
