package com.udacity.asteroidradar.retrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.data.models.PictureOfDay
import com.udacity.asteroidradar.util.ConverterFactory
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class AsteroidsWebService {
    private val moshiBuilder = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    object WebCall{
        private val retrofitBuilder=Retrofit.Builder().addConverterFactory(ConverterFactory()).baseUrl(BASE_URL).build()
        val apiCall: ApiCall by lazy {
            retrofitBuilder.create(ApiCall::class.java)
        }
    }
}

