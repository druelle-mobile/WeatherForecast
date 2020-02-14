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
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.ForecastDao
import ovh.geoffrey_druelle.weatherforecast.data.local.database.WeatherForecastDatabase
import ovh.geoffrey_druelle.weatherforecast.data.local.model.CityEntity
import ovh.geoffrey_druelle.weatherforecast.data.local.model.ForecastEntity
import ovh.geoffrey_druelle.weatherforecast.data.repository.CityRepository
import ovh.geoffrey_druelle.weatherforecast.data.repository.ForecastRepository
import ovh.geoffrey_druelle.weatherforecast.utils.getOrAwaitValue

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, application = WeatherForecastApplication::class)
class ForecastRepositoryTest : CoroutineScope {

    override val coroutineContext
        get() = Dispatchers.Main

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var forecastRepository: ForecastRepository
    private lateinit var cityRepository: CityRepository
    private lateinit var weatherForecastDatabase: WeatherForecastDatabase
    private lateinit var forecastDao: ForecastDao

    @Before
    fun setUp() {
        weatherForecastDatabase = Room.inMemoryDatabaseBuilder(
            WeatherForecastApplication.appContext,
            WeatherForecastDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        forecastDao = weatherForecastDatabase.forecastDao()

        forecastRepository = ForecastRepository(WeatherForecastApplication.instance)
        cityRepository = CityRepository(WeatherForecastApplication.instance)
    }

    @After
    fun after() {
        weatherForecastDatabase.close()
        stopKoin()
    }

    @Test
    fun `insert forecasts and get them`() {
        runBlocking {
            withContext(Dispatchers.IO) {
                // First insert city
                cityRepository.insert(CityEntity(12345,"Paris","FR",0.0,0.0,123456,123456,1))
                // Insert forecasts
                forecastRepository.insert(ForecastEntity(1,12345,0,"azerty",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))
                forecastRepository.insert(ForecastEntity(2,12345,0,"qsdfgh",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))
                forecastRepository.insert(ForecastEntity(3,12345,0,"wxcvbn",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))
                forecastRepository.insert(ForecastEntity(4,12345,0,"qwerty",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))

                val cityList = forecastRepository.getForecastList().getOrAwaitValue()
                val cityListFirstId = cityList[0].id
                val cityListSecondCityId = cityList[1].cityId
                val cityListThirdDtTxt = cityList[2].dt_txt
                val cityListFourthId = cityList[3].id

                val attendeeFirstId = 1L
                val attendeeSecondCityId = 12345L
                val attendeeThirdDtTxt = "wxcvbn"
                val attendeeFourthId = 4L

                assertEquals(attendeeFirstId, cityListFirstId)
                assertEquals(attendeeSecondCityId, cityListSecondCityId)
                assertEquals(attendeeThirdDtTxt, cityListThirdDtTxt)
                assertEquals(attendeeFourthId, cityListFourthId)
            }
        }
    }


    @Test
    fun `insert forecasts, count, delete, count`() {
        runBlocking {
            withContext(Dispatchers.IO) {
                // First insert city
                cityRepository.insert(CityEntity(12345,"Paris","FR",0.0,0.0,123456,123456,1))
                // Insert forecasts
                forecastRepository.insert(ForecastEntity(1,12345,0,"azerty",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))
                forecastRepository.insert(ForecastEntity(2,12345,0,"qsdfgh",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))
                forecastRepository.insert(ForecastEntity(3,12345,0,"wxcvbn",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))
                forecastRepository.insert(ForecastEntity(4,12345,0,"qwerty",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))


                val attendeeBeforeDel = 4
                val count = forecastRepository.countForecastEntries()

                assertEquals(attendeeBeforeDel, count)

                ////////////////

                forecastRepository.deleteAll()

                val attendeeAfterDel = 0
                val countAfterDel = forecastRepository.countForecastEntries()

                assertEquals(attendeeAfterDel, countAfterDel)
            }
        }
    }


    @Test
    fun `insert forecasts and get city id`() {
        runBlocking {
            withContext(Dispatchers.IO) {
                // First insert city
                cityRepository.insert(CityEntity(12345,"Paris","FR",0.0,0.0,123456,123456,1))
                // Insert forecasts
                forecastRepository.insert(ForecastEntity(1,12345,0,"azerty",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))
                forecastRepository.insert(ForecastEntity(2,12345,0,"qsdfgh",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))
                forecastRepository.insert(ForecastEntity(3,12345,0,"wxcvbn",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))
                forecastRepository.insert(ForecastEntity(4,12345,0,"qwerty",0,0.0,0,0,0,0,0.0,0.0,0.0,0.0,0.0,0.0,"",0,"",0,0.0))

                val cityId = forecastRepository.getForecastCityId()
                val attendee = 12345L

                assertEquals(attendee, cityId)
            }
        }
    }
}