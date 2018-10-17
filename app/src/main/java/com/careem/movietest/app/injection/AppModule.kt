package com.careem.movietest.app.injection

import android.content.Context
import com.careem.movietest.app.base.MasterApplication
import com.careem.movietest.app.util.ResourceManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: MasterApplication) {

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application

    @Provides
    @Singleton
    fun providesApplication(): MasterApplication = application

    @Provides
    @Singleton
    fun providesResourceManager(): ResourceManager = ResourceManager(application)

    @Provides
    @Singleton
    fun providesGson(): Gson =
            GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS")
                    .serializeNulls()
                    .create()
}