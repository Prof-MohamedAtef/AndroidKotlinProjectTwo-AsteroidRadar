package com.udacity.asteroidradar.data

import android.util.Log
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.retrofit.AsteroidsWebService
import com.udacity.asteroidradar.data.room.AsteroidsDatabase
import com.udacity.asteroidradar.util.DateBuilder.Companion.sevenDaysLater
import com.udacity.asteroidradar.util.DateBuilder.Companion.today
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainRepository (private val asteroidsDatabase: AsteroidsDatabase){

    suspend fun callAsteroidsApi(){
        try {
            withContext(Dispatchers.IO){
                val picOfDay= AsteroidsWebService.WebCall.apiCall.getPicOfDay()
                val asteroidsResponse= AsteroidsWebService.WebCall.apiCall.getAsteroidsInSevenDays(
                    today,
                    sevenDaysLater,
                )
                val asteroidsArr= parseAsteroidsJsonResult(JSONObject(asteroidsResponse))
                    .toTypedArray()
                if (picOfDay.mediaType=="image"){
                    asteroidsDatabase.pictureOfDayDao.insertPictureOfDay(picOfDay)
                }
                asteroidsDatabase.asteroidsDao.insertAsteroids(*asteroidsArr)
            }
        }catch (exception: Exception){
            Log.e("Api|DB : Exception: ","Unable Calling Api or Updating DB")
            return
        }
    }

    suspend fun returnTodayAsteroidsFromDB():List<Asteroid>{
        return asteroidsDatabase.asteroidsDao.getAsteroidsToday(today)
    }

    suspend fun returnNextSevenDaysAsteroidsFromDB():List<Asteroid>{
        return asteroidsDatabase.asteroidsDao.getAsteroidsNextSevenDays(today, sevenDaysLater)
    }

    suspend fun selectAsteroidsFromDB():List<Asteroid>{
        return asteroidsDatabase.asteroidsDao.getSavedAsteroids()
    }

    suspend fun removeAsteroidsEarlierThanToday(){
        return asteroidsDatabase.asteroidsDao.deleteAsteroidsBeforeToday(today)
    }

    suspend fun getRecentNasaPicDayFromDB(): PictureOfDay {
        return asteroidsDatabase.pictureOfDayDao.getLastlyDownloadedImage()
    }
}