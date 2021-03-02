package com.sahil.practicaltask.utils

import android.text.format.DateFormat
import android.view.View
import java.util.*

object Utility {

    fun timeStamp2DateTime(date: String): String{
        val cal: Calendar = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = date.toLong() * 1000L
        return DateFormat.format("dd-MM-yyyy HH:mm", cal).toString()
    }

    fun timeStamp2Date(date: String): String{
        val cal: Calendar = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = date.toLong() * 1000L
        return DateFormat.format("dd-MM-yyyy", cal).toString()
    }

    fun timeStamp2Time(date: String): String{
        val cal: Calendar = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = date.toLong() * 1000L
        return DateFormat.format("HH:mm", cal).toString()
    }


    fun hideViews(vararg views: View?) {
        for (v in views) {
            if (v != null && v.visibility != View.GONE) {
                if (v.animation != null) {
                    v.clearAnimation()
                }
                v.visibility = View.GONE
            }
        }
    }

    fun showViews(vararg views: View?) {
        for (v in views) {
            if (v != null && v.visibility != View.VISIBLE) {
                v.visibility = View.VISIBLE
            }
        }
    }
}