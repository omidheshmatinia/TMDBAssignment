package com.careem.movietest.app.util

import android.content.Context
import android.content.res.Resources




class ResourceManager(var applicationContext: Context) {

    fun getColumnSize():Int{
        val scaleFactor = Resources.getSystem().displayMetrics.density * 180
        val number = Resources.getSystem().displayMetrics.widthPixels
        return (number.toFloat() / scaleFactor.toFloat()).toInt()
    }

}