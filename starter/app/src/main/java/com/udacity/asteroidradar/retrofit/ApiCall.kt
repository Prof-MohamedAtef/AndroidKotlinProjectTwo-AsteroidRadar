package com.udacity.asteroidradar.retrofit

import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.util.ConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCall {

    @ConverterFactory.ScalarsConverter
    @GET("neo/rest/v1/feed?")
    suspend fun getAsteroidsInSevenDays(
        @Query("start_date") today:String,
        @Query("end_date") endDay:String,
//        @Query("api_key") apiKey:String
    ): String

    @ConverterFactory.MoshiConverter
    @GET("planetary/apod?")
    suspend fun getPicOfDay(
//        @Query("api_key") apiKey: String
    ): PictureOfDay
}