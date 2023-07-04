package ru.ilya.spacexrockets.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.ilya.spacexrockets.data.local.dao.LaunchesDao
import ru.ilya.spacexrockets.data.local.dao.RocketsDao
import ru.ilya.spacexrockets.data.local.entity.LaunchEntity
import ru.ilya.spacexrockets.data.local.entity.RocketEntity

@Database(
    entities = [RocketEntity::class, LaunchEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rocketsDao(): RocketsDao

    abstract fun launchesDao(): LaunchesDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "rockets.db"

        fun getInstance(context: Context): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}