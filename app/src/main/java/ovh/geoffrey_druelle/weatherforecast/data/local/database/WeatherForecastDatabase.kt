package ovh.geoffrey_druelle.weatherforecast.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ovh.geoffrey_druelle.weatherforecast.data.local.converter.*
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.*
import ovh.geoffrey_druelle.weatherforecast.data.local.database.WeatherForecastDatabase.Companion.databaseVersion
import ovh.geoffrey_druelle.weatherforecast.data.local.model.*
import ovh.geoffrey_druelle.weatherforecast.utils.DB_NAME

@Database(
    entities = [
        ForecastEntity::class,
        CityEntity::class,
        ListItemEntity::class,
        WeatherEntity::class
    ],
    version = databaseVersion,
    exportSchema = false
)
abstract class WeatherForecastDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun forecastDao(): ForecastDao
    abstract fun listItemDao(): ListItemDao
    abstract fun weatherDao(): WeatherDao

            companion object {
        @Volatile
        private var instance: WeatherForecastDatabase? = null
        const val databaseVersion = 1

        fun getInstance(context: Context): WeatherForecastDatabase =
            instance ?: synchronized(this) {
                instance ?: build(context).also {
                    instance = it
                }
            }

        private fun build(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherForecastDatabase::class.java,
                DB_NAME
            )
                .addMigrations(nToNPlusOneMigration)
                .build()

        private val nToNPlusOneMigration =
            object : Migration(
                databaseVersion,
                databaseVersion + 1
            ) {
                override fun migrate(database: SupportSQLiteDatabase) {}
            }
    }
}
