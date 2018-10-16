package com.careem.movietest.app.base


interface MasterFragmentPresenterInterface<T : MasterFragmentViewInterface> {
    fun attachView(view:T)
    fun destroy()
}