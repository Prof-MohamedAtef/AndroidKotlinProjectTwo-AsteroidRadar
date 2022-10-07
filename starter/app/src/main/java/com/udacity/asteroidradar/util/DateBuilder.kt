package com.udacity.asteroidradar.util


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class DateBuilder {

    object DateBuilderObject{

        fun returnNowDate():LocalDate= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        fun localDate():String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            returnNowDate().atStartOfDay().toString()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        fun weekFromToday():LocalDate= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            returnNowDate().plusWeeks(1)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

    companion object{
        val today= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateBuilder.DateBuilderObject.returnNowDate().toString()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val sevenDaysLater= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateBuilderObject.returnNowDate().plusWeeks(1).toString()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
}