package com.udacity.asteroidradar.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.PictureOfDay

@Dao
interface PictureOfTheDayDao {
    @Query("SELECT * FROM TblImages ORDER BY date DESC LIMIT 1")
    suspend fun getLastlyDownloadedImage(): PictureOfDay

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureOfDay(imageOfTheDay: PictureOfDay)
}