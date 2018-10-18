package com.careem.movietest.app.base


interface MasterActivityPresenterInterface<T : MasterActivityViewInterface> {
    fun attachView(view:T)
    fun destroy()
}