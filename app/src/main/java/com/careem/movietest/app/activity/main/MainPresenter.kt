package com.careem.movietest.app.activity.main


import com.careem.movietest.app.base.MasterActivityPresenter


class MainPresenter : MasterActivityPresenter<MainContract.View>(), MainContract.Presenter {

    override fun attachView(view: MainContract.View) {
        super.attachView(view)
        view.showMovieListFragment()
    }

}