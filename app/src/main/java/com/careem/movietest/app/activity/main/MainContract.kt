package com.careem.movietest.app.activity.main

import com.careem.movietest.app.base.MasterActivityPresenterInterface
import com.careem.movietest.app.base.MasterActivityViewInterface


interface MainContract {

    interface View : MasterActivityViewInterface {
        fun showMovieListFragment()
    }


    interface Presenter : MasterActivityPresenterInterface<View>
}