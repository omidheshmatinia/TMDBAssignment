package com.careem.movietest.app.base

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.careem.movietest.app.customview.CustomDialogNoConnection
import com.careem.movietest.app.interfaces.NoConnectionInterface


open class MasterFragment:Fragment(),MasterFragmentViewInterface {

    override fun showRetrySnackBar(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNoConnectionDialog(listener: NoConnectionInterface) {
        context?.run {
            val dialog = CustomDialogNoConnection(this,listener)
            dialog.setCancelable(false)
            dialog.show()
        }
    }



    fun showSnack(parent: ViewGroup, message: String, length: Int,
                  actionLabel: String? = null, action: ((View) -> Unit)? = null,
                  callback: ((Snackbar) -> Unit)? = null) {

        val snack = Snackbar.make(parent, message, length)
                .apply {
                    if (actionLabel != null) {
                        setAction(actionLabel, action)
                    }

                    callback?.invoke(this)
                }

        snack.show()
    }

    override fun toast(message : String) {
        context?.run {
            Toast.makeText(this, message.trim(), Toast.LENGTH_LONG).show()
        }
    }

    override fun toast(messageId : Int) {
        context?.run {
            Toast.makeText(this, this.getString(messageId).trim(), Toast.LENGTH_LONG).show()
        }
    }
}