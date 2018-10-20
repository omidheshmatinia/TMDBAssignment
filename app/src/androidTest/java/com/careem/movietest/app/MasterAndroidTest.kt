package com.careem.movietest.app

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.mockito.Mockito


open class MasterAndroidTest {


    protected fun fixRxJavaSchedulers() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { h -> Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { h -> Schedulers.trampoline() }
    }

    // For error in kotlin with mockito any which return null
    protected fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T
}