package com.udacity.asteroidradar.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidsDao {
    @Query("SELECT * FROM TBLAsteroids WHERE closeApproachDate = :today")
    suspend fun getAsteroidsToday(today: String): List<Asteroid>

    @Query("SELECT * FROM TBLAsteroids WHERE closeApproachDate BETWEEN :today AND :sevenDaysFromToday ORDER BY closeApproachDate DESC")
    suspend fun getAsteroidsNextSevenDays(today: String, sevenDaysFromToday: String): List<Asteroid>

    @Query("SELECT * FROM TBLAsteroids ORDER BY closeApproachDate DESC")
    suspend fun getSavedAsteroids(): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(vararg asteroids: Asteroid)

    @Query("DELETE FROM TBLAsteroids WHERE closeApproachDate < :today")
    suspend fun deleteAsteroidsBeforeToday(today: String)
}