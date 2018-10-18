package com.careem.movietest.app.fragment.movielist

import com.careem.movietest.app.base.MasterFragmentPresenterInterface
import com.careem.movietest.app.base.MasterFragmentViewInterface
import com.careem.movietest.app.interfaces.SnackbarActionListener
import com.careem.movietest.app.model.MovieModel


interface MovieListFContract {

    enum class ProgressType{
        CenterProgressBar,BottomProgressbar
    }

    interface View : MasterFragmentViewInterface {
        fun showProgress(type:ProgressType)
        fun hideProgress(type:ProgressType)
        fun setupList(items:List<MovieModel>,columnSize:Int)
        fun addNewItemsToList(beginIndex: Int, newItemsSize: Int)
        fun showRetrySnack(msg:String,listener: SnackbarActionListener)
        fun showDetailFragment(movie: MovieModel)
    }

    interface Presenter : MasterFragmentPresenterInterface<View>{
        fun movieListItemClicked(movie:MovieModel)
        fun movieListReachEnd()
    }
}