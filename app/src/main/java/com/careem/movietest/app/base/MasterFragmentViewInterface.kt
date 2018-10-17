package com.careem.movietest.app.base

import android.support.annotation.StringRes
import com.careem.movietest.app.interfaces.NoConnectionInterface


interface MasterFragmentViewInterface {
    fun showRetrySnackBar(error: String)
    fun toast(message: String)
    fun toast(@StringRes messageId: Int)
    fun showNoConnectionDialog(listener:NoConnectionInterface)
}