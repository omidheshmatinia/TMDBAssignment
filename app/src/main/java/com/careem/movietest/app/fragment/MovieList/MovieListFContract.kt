package com.careem.movietest.app.fragment.movielist

import com.careem.movietest.app.base.MasterFragmentPresenterInterface
import com.careem.movietest.app.base.MasterFragmentViewInterface
import com.careem.movietest.app.interfaces.SnackbarActionListener
import com.careem.movietest.app.model.MovieModel
import java.util.*


interface MovieListFContract {

    enum class ProgressType{
        CenterProgressBar,BottomProgressbar
    }

    enum class ButtonType{
        EndDate,StartDate
    }

    interface View : MasterFragmentViewInterface {
        fun showProgress(type:ProgressType)
        fun hideProgress(type:ProgressType)
        fun setupList(items:List<MovieModel>,columnSize:Int)
        fun addNewItemsToList(beginIndex: Int, newItemsSize: Int)
        fun showRetrySnack(msg:String,listener: SnackbarActionListener)
        fun showDetailFragment(movie: MovieModel)
        fun showDateChoosingBottomSheet()
        fun hideBottomSheet()
        fun showDateRangeDialog(date: Date?, isStartDate: Boolean)
        fun changeButtonText(title: String, type: ButtonType)
        fun clearList()
    }

    interface Presenter : MasterFragmentPresenterInterface<View>{
        fun movieListItemClicked(movie:MovieModel)
        fun movieListReachEnd()
        fun buttonDateRangeClicked()
        fun buttonSearchDateRangeClicked()
        fun buttonStartDateRangeClicked()
        fun buttonEndDateRangeClicked()
        fun userChooseNewDateRange(year: Int, month: Int, day: Int, isStartDate: Boolean)
    }
}