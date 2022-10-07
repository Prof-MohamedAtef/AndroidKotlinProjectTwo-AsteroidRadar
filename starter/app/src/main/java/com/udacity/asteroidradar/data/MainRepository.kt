package com.udacity.asteroidradar.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.models.Asteroid
import com.udacity.asteroidradar.data.models.PictureOfDay
import com.udacity.asteroidradar.retrofit.ApiCall
import com.udacity.asteroidradar.retrofit.AsteroidsWebService
import com.udacity.asteroidradar.data.room.AsteroidsDatabase
import com.udacity.asteroidradar.util.DateBuilder
import com.udacity.asteroidradar.util.DateBuilder.Companion.sevenDaysLater
import com.udacity.asteroidradar.util.DateBuilder.Companion.today
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDate

class MainRepository (private val asteroidsDatabase: AsteroidsDatabase){
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun callAsteroidsApi(){
        try {
            withContext(Dispatchers.IO){
                val picOfDay= AsteroidsWebService.WebCall.apiCall.getPicOfDay(BuildConfig.NasaApiKey)
                val asteroidsResponse= AsteroidsWebService.WebCall.apiCall.getAsteroidsInSevenDays(
                    today,
                    sevenDaysLater,
                    apiKey = BuildConfig.NasaApiKey
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

    suspend fun returnAsteroidsFromDB():List<Asteroid>{
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

    suspend fun getRecentNasaPicDay():PictureOfDay{
        return asteroidsDatabase.pictureOfDayDao.getLastlyDownloadedImage()
    }
}