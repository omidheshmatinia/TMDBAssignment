package com.careem.movietest.app.interfaces


interface SimpleListClickListener<T> {
    fun itemClicked(item:T,position:Int)
    fun lastItemReached()
}