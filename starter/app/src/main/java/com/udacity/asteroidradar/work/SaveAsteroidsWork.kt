package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.MainRepository
import com.udacity.asteroidradar.data.room.AsteroidsDatabase.Companion.getDatabaseInstance
import retrofit2.HttpException

class SaveAsteroidsWork(context: Context, workerParameters: WorkerParameters) :
        CoroutineWorker(context, workerParameters){

    companion object{
        const val DOWNLOAD_ASTEROIDS: String="DOWNLOAD_ASTEROIDS"
    }

    override suspend fun doWork(): Result {
        val database= getDatabaseInstance(context = applicationContext)
        val repository= MainRepository(database)

        return try{
            repository.callAsteroidsApi()
            // delete asteroids before today
            repository.removeAsteroidsEarlierThanToday()
            Result.success()
        }catch (exception: HttpException){
            Result.retry()
        }
    }
}