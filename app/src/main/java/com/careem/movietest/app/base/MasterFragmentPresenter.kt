package com.careem.movietest.app.base

import io.reactivex.disposables.CompositeDisposable


open class MasterFragmentPresenter<T : MasterFragmentViewInterface> :MasterFragmentPresenterInterface<T>{
    protected var compositeDisposable : CompositeDisposable = CompositeDisposable()
    protected var view : T? =null

    override fun destroy() {
        view = null
        compositeDisposable.dispose()
    }

    override fun attachView(view: T) {
        this.view = view
    }
}