package com.careem.movietest.app.base

import android.app.Application
import android.graphics.Bitmap
import com.careem.movietest.app.R
import com.careem.movietest.app.injection.AppComponent
import com.careem.movietest.app.injection.AppModule
import com.careem.movietest.app.injection.DaggerAppComponent
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer
import java.util.*


class MasterApplication:Application() {

    companion object {
        lateinit var instance : MasterApplication
        lateinit var appComponent : AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        instance=this
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        val m = ImageLoader.getInstance()
        val config = ImageLoaderConfiguration.Builder(applicationContext)
                .threadPoolSize(3)
                .diskCacheExtraOptions(480, 320, null)
                .defaultDisplayImageOptions(getDisplayImageOption())
                .build() //discCache(new UnlimitedDiscCache(cacheDir))
        m.init(config)
    }

    private fun getDisplayImageOption(): DisplayImageOptions {
        return DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.bg_primary_1px)
                .showImageForEmptyUri(R.drawable.bg_primary_1px)
                .showImageOnFail(R.drawable.bg_primary_1px)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(FadeInBitmapDisplayer(1000))
                .build()
    }
}