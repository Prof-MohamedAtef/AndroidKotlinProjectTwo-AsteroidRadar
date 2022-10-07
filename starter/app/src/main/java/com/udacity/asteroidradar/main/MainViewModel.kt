package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.data.MainRepository
import com.udacity.asteroidradar.data.models.Asteroid
import com.udacity.asteroidradar.data.models.PictureOfDay
import com.udacity.asteroidradar.data.room.AsteroidsDatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {
    private val _asteroidsResponse= MutableLiveData<List<Asteroid>>()
    val asteroidResponse:LiveData<List<Asteroid>>
        get() = _asteroidsResponse

    private val _picOfDay= MutableLiveData<List<PictureOfDay>>()
    val picOfDay:LiveData<List<PictureOfDay>>
        get() = _picOfDay

    private val databaseInstance= AsteroidsDatabase.getDatabaseInstance(application)
    private val repository=MainRepository(databaseInstance)

    init {
        callApi()
    }

    private fun callApi() {
        try {
            viewModelScope.launch {
                repository.callAsteroidsApi()
                _asteroidsResponse.value=repository.returnAsteroidsFromDB()
                _picOfDay.value= listOf(repository.getRecentNasaPicDayFromDB())
            }
        }catch (exception: Exception){
            Log.e("ViewModel Exception :","Exception in ViewModel")
        }
    }


}