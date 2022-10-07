package com.udacity.asteroidradar.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.data.models.Asteroid
import com.udacity.asteroidradar.data.models.PictureOfDay

@Database(entities = [PictureOfDay::class, Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidsDatabase : RoomDatabase() {
    abstract val asteroidsDao: AsteroidsDao
    abstract val pictureOfDayDao: PictureOfTheDayDao
    companion object{

        @Volatile
        private var INSTANCE: AsteroidsDatabase? = null

        fun getDatabaseInstance(context: Context): AsteroidsDatabase {
            synchronized(this){
                var instance= INSTANCE
                if (instance==null){
                    instance=Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidsDatabase::class.java,
                        "AsteroidsDB"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE =instance
                }
                return instance
            }
        }
    }
}