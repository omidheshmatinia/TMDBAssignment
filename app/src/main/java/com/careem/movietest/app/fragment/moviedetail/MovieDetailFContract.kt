package com.careem.movietest.app.fragment.moviedetail

import com.careem.movietest.app.base.MasterFragmentPresenterInterface
import com.careem.movietest.app.base.MasterFragmentViewInterface
import com.careem.movietest.app.interfaces.SnackbarActionListener
import com.careem.movietest.app.model.MovieModel


interface MovieDetailFContract {

    enum class TextViewType{
        Title,Description,ReleaseDate,VoteAverage,Budget,Revenue,Runtime
    }

    enum class ViewType{
        DetailProgress,CardViewBudget,CardViewRunTime,CardViewRevenue
    }

    interface View : MasterFragmentViewInterface {
        fun setText(text:String,type: MovieDetailFContract.TextViewType)
        fun showPosterImage(url:String)
        fun showBackDropImage(url:String)
        fun setupScrollAndToolbarStatus()
        fun changeLayoutsBasedOnScroll()
        fun hideView(type: ViewType)
        fun showView(type: ViewType)
        fun showRetrySnack(error: String, snackbarActionListener: SnackbarActionListener)
    }

    interface Presenter : MasterFragmentPresenterInterface<View> {
        fun setMovieFromIntent(item: MovieModel?)
        fun scrollChanged()
    }
}