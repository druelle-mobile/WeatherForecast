package ovh.geoffrey_druelle.weatherforecast

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import com.facebook.stetho.BuildConfig
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ovh.geoffrey_druelle.weatherforecast.injection.getModules
import timber.log.Timber
import timber.log.Timber.DebugTree
import timber.log.Timber.d

class WeatherForecastApplication: Application() {

    companion object {
        lateinit var appContext: Context
        lateinit var instance: WeatherForecastApplication

        lateinit var cityIdPref: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        instance = this

        cityIdPref = appContext.getSharedPreferences("CITY_ID", Context.MODE_PRIVATE)

        if (!isRoboUnitTest() || BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            Stetho.initializeWithDefaults(appContext)
        }

        startKoin {
            androidLogger()
            androidContext(this@WeatherForecastApplication)
            modules(getModules())
        }
    }

    private fun isRoboUnitTest(): Boolean {
        return "robolectric" == Build.FINGERPRINT
    }

    fun getVersionName(): String {
        try {
            val packageInfo = this.packageManager.getPackageInfo(packageName, 0)
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            d(e)
        }
        return "0.0.0"
    }
}