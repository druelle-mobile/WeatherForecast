package ovh.geoffrey_druelle.weatherforecast.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ovh.geoffrey_druelle.weatherforecast.data.local.converter.GenericConverter
import ovh.geoffrey_druelle.weatherforecast.data.local.dao.GenericDao
import ovh.geoffrey_druelle.weatherforecast.data.local.database.AppDatabase.Companion.databaseVersion
import ovh.geoffrey_druelle.weatherforecast.data.local.model.GenericModel
import ovh.geoffrey_druelle.weatherforecast.utils.DB_NAME

@Database(
    entities = [GenericModel::class],
    version = databaseVersion,
    exportSchema = false
)
@TypeConverters(GenericConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genericDao(): GenericDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        const val databaseVersion = 1

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: build(context).also {
                    instance = it
                }
            }

        private fun build(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
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
