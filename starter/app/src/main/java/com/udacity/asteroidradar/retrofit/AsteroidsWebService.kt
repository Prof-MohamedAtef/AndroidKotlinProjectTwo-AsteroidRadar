package com.udacity.asteroidradar.retrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.util.ConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class AsteroidsWebService {
    private val moshiBuilder = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    object WebCall{
        private val retrofitBuilder=Retrofit.Builder().addConverterFactory(ConverterFactory()).baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val url = chain
                            .request()
                            .url()
                            .newBuilder()
                            .addQueryParameter("api_key", BuildConfig.NasaApiKey)
                            .build()
                        chain.proceed(chain.request().newBuilder().url(url).build())
                    }.build()
            )
            .build()
        val apiCall: ApiCall by lazy {
            retrofitBuilder.create(ApiCall::class.java)
        }
    }
}

