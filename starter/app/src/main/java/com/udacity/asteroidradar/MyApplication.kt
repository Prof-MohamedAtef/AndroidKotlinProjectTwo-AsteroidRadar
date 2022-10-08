package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.constraintlayout.widget.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.udacity.asteroidradar.work.SaveAsteroidsWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MyApplication:Application() {

    val appCoroutineScope= CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayedWork()
    }

    private fun delayedWork() {
        appCoroutineScope.launch {
            scheduleWork()
        }
    }

    private fun scheduleWork() {
        val constraints= androidx.work.Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequest
            = PeriodicWorkRequestBuilder<SaveAsteroidsWork>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            SaveAsteroidsWork.DOWNLOAD_ASTEROIDS,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}