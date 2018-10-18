package com.careem.movietest.app.base

import android.support.v7.app.AppCompatActivity
import android.widget.Toast


open class MasterActivity :AppCompatActivity(),MasterActivityViewInterface{

    override fun toast(message : String) {
        Toast.makeText(this, message.trim(), Toast.LENGTH_LONG).show()
    }

    override fun toast(messageId : Int) {
        Toast.makeText(this, this.getString(messageId).trim(), Toast.LENGTH_LONG).show()
    }
}