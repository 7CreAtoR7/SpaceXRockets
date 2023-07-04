package ru.ilya.spacexrockets.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.ilya.spacexrockets.data.local.entity.RocketEntity

@Dao
interface RocketsDao {

    @Query("SELECT * FROM rocketsTable")
    fun getLocalRockets(): List<RocketEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRockets(rockets: List<RocketEntity>)

    @Query("DELETE FROM rocketsTable")
    suspend fun deleteAllRockets()
}