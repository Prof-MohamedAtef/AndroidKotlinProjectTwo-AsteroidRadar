package com.udacity.asteroidradar

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "TblImages")
data class PictureOfDay(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String)