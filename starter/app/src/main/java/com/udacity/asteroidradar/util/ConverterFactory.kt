package com.udacity.asteroidradar.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Type

class ConverterFactory: Converter.Factory() {
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class MoshiConverter

    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class ScalarsConverter

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        annotations.forEach { annotation ->
            when(annotation){
                is ScalarsConverter ->{
                    return ScalarsConverterFactory.create()
                        .responseBodyConverter(type, annotations, retrofit)
                }
                is MoshiConverter ->{
                    return MoshiConverterFactory.create(moshi)
                        .responseBodyConverter(type, annotations, retrofit)
                }
            }
        }
        return super.responseBodyConverter(type, annotations, retrofit)
    }
}