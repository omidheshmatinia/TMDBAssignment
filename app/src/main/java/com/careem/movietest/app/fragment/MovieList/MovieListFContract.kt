package com.careem.movietest.app.fragment.MovieList

import com.careem.movietest.app.base.MasterFragmentPresenterInterface
import com.careem.movietest.app.base.MasterFragmentViewInterface


interface MovieListFContract {

    interface View : MasterFragmentViewInterface {

    }

    interface Presenter : MasterFragmentPresenterInterface<View>
}