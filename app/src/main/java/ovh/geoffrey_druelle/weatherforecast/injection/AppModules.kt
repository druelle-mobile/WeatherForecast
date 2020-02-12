package ovh.geoffrey_druelle.weatherforecast.injection

import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ovh.geoffrey_druelle.weatherforecast.BuildConfig.OPEN_WEATHER_API_KEY
import ovh.geoffrey_druelle.weatherforecast.data.local.database.WeatherForecastDatabase
import ovh.geoffrey_druelle.weatherforecast.data.remote.api.CitiesListApi
import ovh.geoffrey_druelle.weatherforecast.data.remote.api.OpenWeatherMapApi
import ovh.geoffrey_druelle.weatherforecast.ui.forecast.ForecastListViewModel
import ovh.geoffrey_druelle.weatherforecast.ui.main.MainActivityViewModel
import ovh.geoffrey_druelle.weatherforecast.ui.splash.SplashScreenViewModel
import ovh.geoffrey_druelle.weatherforecast.utils.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModules = module {
    // Database modules part
    single { WeatherForecastDatabase.getInstance(androidApplication()) }
    single { get<WeatherForecastDatabase>().cityDao() }
    single { get<WeatherForecastDatabase>().citiesListDao() }
    single { get<WeatherForecastDatabase>().forecastDao() }

    // Network modules part
    single { Cache(androidApplication().cacheDir, 20L * 1024 * 1024) }
    single(named("owm_okhttp")) { provideOpenWeatherMapOkHttpClient(get()) }
    single(named("owm_retrofit")) { provideOpenWeatherMapRetrofitClient(get(named("owm_okhttp"))) }
    single(named("owm_api")) { provideOpenWeatherMapApiService(get(named("owm_retrofit"))) }
    single(named("cities_okhttp")) { provideCitiesOkHttpClient() }
    single(named("cities_retrofit")) { provideCitiesRetrofitClient(get(named("cities_okhttp"))) }
    single(named("cities_api")) { provideCitiesApiService(get(named("cities_retrofit"))) }

    // ViewModels modules part
    viewModel { MainActivityViewModel() }
    viewModel { SplashScreenViewModel(owmApi = get(named("owm_api")), cityApi = get(named("cities_api"))) }
    viewModel { ForecastListViewModel() }

    // Provider module part
}

fun getModules(): List<Module> {
    return listOf(appModules)
}

// Network providers
// Totally subjective.
// Must be modified depending of usage
fun provideOpenWeatherMapOkHttpClient(cache: Cache): OkHttpClient {
    return OkHttpClient.Builder().apply {
        cache(cache)
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
        addInterceptor(openWeatherMapApiInterceptor())
    }.build()
}

fun openWeatherMapApiInterceptor() = Interceptor { chain ->
chain.proceed(
        chain.request().newBuilder()
            .apply {
                header("Accept", "application/json")
                header("Content-Type", "application/json; charset=utf-8")
            }
            .url(
                chain.request().url().newBuilder()
                    .addQueryParameter("appid", OPEN_WEATHER_API_KEY)
                    .build()
            )
            .build()
    )
}

fun provideOpenWeatherMapRetrofitClient(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOpenWeatherMapApiService(retrofit: Retrofit): OpenWeatherMapApi =
    retrofit.create(OpenWeatherMapApi::class.java)


fun provideCitiesOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
        addInterceptor(openCitiesInterceptor())
    }.build()
}

fun openCitiesInterceptor() = Interceptor { chain ->
    chain.proceed(
        chain.request().newBuilder()
            .apply {
                header("Accept", "application/json")
                header("Content-Type", "application/json; charset=utf-8")
            }
            .build()
    )
}

fun provideCitiesRetrofitClient(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(CITIES_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideCitiesApiService(retrofit: Retrofit): CitiesListApi =
    retrofit.create(CitiesListApi::class.java)
