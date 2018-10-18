package com.careem.movietest.app.base

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.careem.movietest.app.customview.CustomDialogNoConnection
import com.careem.movietest.app.interfaces.NoConnectionInterface


open class MasterFragment:Fragment(),MasterFragmentViewInterface {

    private var snackbar:Snackbar? = null


    override fun showNoConnectionDialog(listener: NoConnectionInterface) {
        context?.run {
            val dialog = CustomDialogNoConnection(this,listener)
            dialog.setCancelable(false)
            dialog.show()
        }
    }

    override fun dismissSnackBar(){
        snackbar?.dismiss()
        snackbar = null
    }

    fun showSnack(parent: ViewGroup, message: String, length: Int,
                  actionLabel: String? = null, action: ((View) -> Unit)? = null,
                  callback: ((Snackbar) -> Unit)? = null) {

        snackbar = Snackbar.make(parent, message, length)
                .apply {
                    if (actionLabel != null) {
                        setAction(actionLabel, action)
                    }

                    callback?.invoke(this)
                }

        snackbar!!.show()
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