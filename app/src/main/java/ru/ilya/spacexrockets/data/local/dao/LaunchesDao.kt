package ru.ilya.spacexrockets.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.ilya.spacexrockets.data.local.entity.LaunchEntity


@Dao
interface LaunchesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunches(launches: List<LaunchEntity>)

    @Query("DELETE FROM launchesTable WHERE rocket_name LIKE '%' || :rocketName || '%'")
    suspend fun deleteLaunchesByRocketName(rocketName: String)

    @Query("SELECT * FROM launchesTable WHERE rocket_name LIKE '%' || :rocketName || '%'")
    fun getLaunchesByRocketName(rocketName: String): List<LaunchEntity>
}