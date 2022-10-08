package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.data.MainRepository
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.data.room.AsteroidsDatabase
import com.udacity.asteroidradar.util.LoadingStatus
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {
    private val _asteroidsResponse= MutableLiveData<List<Asteroid>>()
    val asteroidResponse:LiveData<List<Asteroid>>
        get() = _asteroidsResponse

    private val _picOfDay= MutableLiveData<List<PictureOfDay>>()
    val picOfDay:LiveData<List<PictureOfDay>>
        get() = _picOfDay

    private val _loadingStatus=MutableLiveData<LoadingStatus>()
    val loadingStatus:LiveData<LoadingStatus>
        get() = _loadingStatus

    private val databaseInstance= AsteroidsDatabase.getDatabaseInstance(application)
    private val repository=MainRepository(databaseInstance)


    init {
        callApi()
    }

    private fun callApi() {
        try {
            viewModelScope.launch {
                _loadingStatus.value=LoadingStatus.LOADING
                repository.callAsteroidsApi()
                _asteroidsResponse.value=repository.selectAsteroidsFromDB()
                _picOfDay.value= listOf(repository.getRecentNasaPicDayFromDB())
                if (asteroidResponse.value?.isEmpty()==true || picOfDay.value?.isEmpty()==true){
                    _loadingStatus.value=LoadingStatus.ERROR
                }else{
                    _loadingStatus.value=LoadingStatus.DONE
                }
            }
        }catch (exception: Exception){
            _loadingStatus.value=LoadingStatus.ERROR
            Log.e("ViewModel Exception :","Exception in ViewModel")
        }
    }

    suspend fun returnWeekData(){
        _asteroidsResponse.value=repository.returnNextSevenDaysAsteroidsFromDB()
    }

    suspend fun returnTodayData(){
        _asteroidsResponse.value=repository.returnTodayAsteroidsFromDB()
    }

    suspend fun returnSavedAsteroids(){
        _asteroidsResponse.value=repository.selectAsteroidsFromDB()
    }


}