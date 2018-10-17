package com.careem.movietest.app.customview

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import com.careem.movietest.app.R
import com.careem.movietest.app.interfaces.NoConnectionInterface
import kotlinx.android.synthetic.main.dialog_no_connection.*


class CustomDialogNoConnection(ct: Context,var listener: NoConnectionInterface) :Dialog(ct), View.OnClickListener {

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.button_dialog_noConnection_tryAgain -> {
                cancel()
                listener.tryAgain()
            }
            R.id.imageView_dialog_noConnection_cancel -> {
                cancel()
                listener.cancelDialog()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_no_connection)
        window.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
        button_dialog_noConnection_tryAgain.setOnClickListener(this)
        imageView_dialog_noConnection_cancel.setOnClickListener(this)
    }
}