package com.careem.movietest.app

import android.support.test.runner.AndroidJUnitRunner
import android.app.KeyguardManager
import android.content.Context
import android.os.PowerManager

class CustomTestRunner : AndroidJUnitRunner(){

    override fun onStart() {

        runOnMainSync {
            val app = this@CustomTestRunner.targetContext.applicationContext
            val name = CustomTestRunner::class.java.simpleName
            unlockScreen(app, name)
            keepScreenAwake(app, name)
        }

        super.onStart()
    }

    private fun keepScreenAwake(app: Context, name: String) {
        val power = app.getSystemService(Context.POWER_SERVICE) as PowerManager
        power.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE, name)
                .acquire()
    }
//
    private fun unlockScreen(app: Context, name: String) {
        val keyguard = app.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        keyguard.newKeyguardLock(name).disableKeyguard()
    }
}