package com.careem.movietest.app.fragment.MovieList


import com.careem.movietest.app.base.MasterFragmentPresenter
import com.careem.movietest.app.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.*


class MovieListFPresenter(var movieRep:MovieRepository) : MasterFragmentPresenter<MovieListFContract.View>(), MovieListFContract.Presenter {

    private var pageIndex = 1
    private var startDate:Date? = null
    private var endDate:Date? = null

    override fun attachView(view: MovieListFContract.View) {
        super.attachView(view)
        getItemsFromServer(pageIndex,startDate,endDate)
    }

    private fun getItemsFromServer(page: Int,beginDate:Date?,endDate:Date?) {
        val movieListObservable= if(beginDate==null&&endDate==null){
            movieRep.getRecentMoviesFromServer(page)
        } else {
            movieRep.getRecentMoviesFilteredByDateFromServer(page,beginDate,endDate)
        }
        movieListObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {
                    var items = it
                    pageIndex++
                },{
                    var itemsd = 123
                    var index = 345345
                })
                .addTo(compositeDisposable)
    }

}