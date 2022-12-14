package com.udacity.asteroidradar

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "TblImages")
data class PictureOfDay(
    @PrimaryKey
    val date: String,
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String)