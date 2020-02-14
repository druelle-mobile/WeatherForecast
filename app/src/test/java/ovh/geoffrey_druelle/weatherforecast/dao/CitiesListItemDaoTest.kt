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
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.CitiesListItemDao
import ovh.geoffrey_druelle.weatherforecast.data.local.database.WeatherForecastDatabase
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CitiesListItemEntity
import ovh.geoffrey_druelle.weatherforecast.utils.getOrAwaitValue

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, application = WeatherForecastApplication::class)
class CitiesListItemDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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
    }

    @After
    fun after() {
        weatherForecastDatabase.close()
        stopKoin()
    }

    @Test
    fun `insert cities and count entries`() {
        citiesListItemDao.insert(CitiesListItemEntity(1, "Paris", "FR"))
        citiesListItemDao.insert(CitiesListItemEntity(2, "London", "GB"))
        citiesListItemDao.insert(CitiesListItemEntity(3, "Washington", "US"))

        val attendee = 3
        val count = citiesListItemDao.getLength()

        assertEquals(attendee, count)
    }

    @Test
    fun `insert continents, count entries`() {
        citiesListItemDao.insert(CitiesListItemEntity(6255147, "Asia", ""))
        citiesListItemDao.insert(CitiesListItemEntity(6255148, "Europe", ""))
        citiesListItemDao.insert(CitiesListItemEntity(6255149, "North America", ""))
        citiesListItemDao.insert(CitiesListItemEntity(6255150, "South America", ""))
        citiesListItemDao.insert(CitiesListItemEntity(6295630, "Earth", ""))
        citiesListItemDao.insert(CitiesListItemEntity(6255146, "Africa", ""))
        citiesListItemDao.insert(CitiesListItemEntity(6255151, "Oceania", ""))
        citiesListItemDao.insert(CitiesListItemEntity(6255152, "Antartica", ""))

        val attendeeBeforeDel = 8
        val count = citiesListItemDao.getLength()

        assertEquals(attendeeBeforeDel, count)
    }

    @Test
    fun `delete continents and count entries`() {
        `insert continents, count entries`()

        citiesListItemDao.deleteAsia()
        citiesListItemDao.deleteEurope()
        citiesListItemDao.deleteNorthAmerica()
        citiesListItemDao.deleteSouthAmerica()
        citiesListItemDao.deleteEarth()
        citiesListItemDao.deleteAfrica()
        citiesListItemDao.deleteOceania()
        citiesListItemDao.deleteAntartica()

        val attendeeAfterDel = 0
        val countAfterDel = citiesListItemDao.getLength()

        assertEquals(attendeeAfterDel, countAfterDel)
    }

    @Test
    fun `insert 3 cities and get one by name`() {
        citiesListItemDao.insert(CitiesListItemEntity(1, "Paris", "FR"))
        citiesListItemDao.insert(CitiesListItemEntity(2, "London", "GB"))
        citiesListItemDao.insert(CitiesListItemEntity(3, "Washington", "US"))

        val city = citiesListItemDao.findCitiesFromName("Paris").getOrAwaitValue()
        val attendeeCityId = 1L
        val attendeeCityName = "Paris"
        val attendeeCityCountry = "FR"

        assertEquals(attendeeCityId, city[0].id)
        assertEquals(attendeeCityName, city[0].name)
        assertEquals(attendeeCityCountry, city[0].country)
    }

    @Test
    fun `insert cities and get them by name and country asc`() {
        citiesListItemDao.insert(CitiesListItemEntity(1, "ParisUS", "US"))
        citiesListItemDao.insert(CitiesListItemEntity(2, "ParisFR", "FR"))
        citiesListItemDao.insert(CitiesListItemEntity(3, "ParisGB", "GB"))

        val city = citiesListItemDao.findCitiesFromName("Paris%").getOrAwaitValue()
        val attendeeFirstCityId = 2L
        val attendeeSecondCityId = 3L
        val attendeeThirdCityId = 1L

        assertEquals(attendeeFirstCityId, city[0].id)
        assertEquals(attendeeSecondCityId, city[1].id)
        assertEquals(attendeeThirdCityId, city[2].id)
    }


    @Test
    fun `insert 3 cities and get id of one by name and country`() {
        citiesListItemDao.insert(CitiesListItemEntity(1, "Paris", "FR"))
        citiesListItemDao.insert(CitiesListItemEntity(2, "London", "GB"))
        citiesListItemDao.insert(CitiesListItemEntity(3, "Washington", "US"))

        val cityId = citiesListItemDao.getCityIdFromNameAndCountry("Paris", "FR")
        val attendee = 1L
        assertEquals(attendee, cityId)
    }
}