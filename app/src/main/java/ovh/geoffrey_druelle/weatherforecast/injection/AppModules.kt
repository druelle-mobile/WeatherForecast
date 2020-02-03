package ovh.geoffrey_druelle.weatherforecast.injection

import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import ovh.geoffrey_druelle.weatherforecast.data.local.database.AppDatabase
import ovh.geoffrey_druelle.weatherforecast.data.remote.api.GenericApi
import ovh.geoffrey_druelle.weatherforecast.ui.generic.GenericViewModel
import ovh.geoffrey_druelle.weatherforecast.ui.generic2.Generic2ViewModel
import ovh.geoffrey_druelle.weatherforecast.utils.BASE_URL
import ovh.geoffrey_druelle.weatherforecast.utils.CONNECT_TIMEOUT
import ovh.geoffrey_druelle.weatherforecast.utils.READ_TIMEOUT
import ovh.geoffrey_druelle.weatherforecast.utils.WRITE_TIMEOUT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModules = module {
    // Database modules part
    single { AppDatabase.getInstance(androidApplication()) }
    single { get<AppDatabase>().genericDao() }

    // Network modules part
    single { Cache(androidApplication().cacheDir, 20L * 1024 * 1024) }
    single { provideOkHttpClient(get()) }
    single { provideRetrofitClient(get()) }
    single { provideApiService(get()) }

    // ViewModels modules part
    viewModel { GenericViewModel() }
    viewModel { Generic2ViewModel() }
}

fun getModules(): List<Module> {
    return listOf(appModules)
}

// Network providers
// Totally subjective.
// Must be modified depending of usage
fun provideOkHttpClient(cache: Cache): OkHttpClient {
    return OkHttpClient.Builder().apply {
        cache(cache)
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
        addInterceptor(apiInterceptor())
    }.build()
}

fun apiInterceptor() = Interceptor { chain ->
    chain.proceed(chain.request().newBuilder()
        .apply {
            header("Accept", "application/json")
            header("Content-Type","application/json; charset=utf-8")
        }
        .build())
}

fun provideRetrofitClient(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideApiService(retrofit: Retrofit): GenericApi
        = retrofit.create(GenericApi::class.java)
