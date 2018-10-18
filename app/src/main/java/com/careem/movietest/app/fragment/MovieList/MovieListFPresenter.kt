package com.careem.movietest.app.fragment.movielist


import com.careem.movietest.app.base.MasterFragmentPresenter
import com.careem.movietest.app.interfaces.NoConnectionInterface
import com.careem.movietest.app.interfaces.SnackbarActionListener
import com.careem.movietest.app.model.MovieModel
import com.careem.movietest.app.model.exception.NoNetworkException
import com.careem.movietest.app.model.exception.WebApiException
import com.careem.movietest.app.repository.MovieRepository
import com.careem.movietest.app.util.ResourceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.*


class MovieListFPresenter(var movieRep:MovieRepository,var resManager:ResourceManager) : MasterFragmentPresenter<MovieListFContract.View>(), MovieListFContract.Presenter {

    private var pageIndex = 1
    private var startDate:Date? = null
    private var endDate:Date? = null
    private var movies:MutableList<MovieModel> = mutableListOf()

    override fun attachView(view: MovieListFContract.View) {
        super.attachView(view)
        view.setupList(movies,resManager.getColumnSize())
        getItemsFromServer(pageIndex,startDate,endDate)
    }

    override fun movieListReachEnd() {
        view?.dismissSnackBar()
        getItemsFromServer(pageIndex,startDate,endDate)
    }

    private fun getItemsFromServer(page: Int, beginDate:Date?, endDate:Date?) {
        val movieListObservable= if(beginDate==null&&endDate==null){
            movieRep.getRecentMoviesFromServer(page)
        } else {
            movieRep.getRecentMoviesFilteredByDateFromServer(page,beginDate,endDate)
        }
        movieListObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if(page == 1)
                        view?.showProgress(MovieListFContract.ProgressType.CenterProgressBar)
                    else {
                        view?.dismissSnackBar()
                        view?.showProgress(MovieListFContract.ProgressType.BottomProgressbar)
                    }
                }
                .doFinally {
                    view?.hideProgress(MovieListFContract.ProgressType.CenterProgressBar)
                    view?.hideProgress(MovieListFContract.ProgressType.BottomProgressbar)
                }
                .subscribe( {
                    movies.addAll(it.results)
                    view?.addNewItemsToList(movies.size-it.results.size,it.results.size)
                    pageIndex++
                },{
                    when(it){
                        is NoNetworkException -> showNoNetworkDialog(page,beginDate,endDate,it.message?:"Connection Error")
                        else -> showRetrySnackBar(it.message?:"Error",page,beginDate,endDate)
                    }
                })
                .addTo(compositeDisposable)
    }

    private fun showRetrySnackBar(error: String, page: Int, beginDate: Date?, endDate: Date?) {
        view?.showRetrySnack(error,object:SnackbarActionListener{
            override fun actionClicked() {
                getItemsFromServer(page,beginDate,endDate)
            }
        })
    }

    private fun showNoNetworkDialog(page: Int, beginDate: Date?, endDate: Date?,error:String) {
        view?.showNoConnectionDialog(object :NoConnectionInterface{
            override fun tryAgain() {
                getItemsFromServer(page,beginDate,endDate)
            }
            override fun cancelDialog() {
                showRetrySnackBar(error,page,beginDate,endDate)
            }
        })
    }

    override fun movieListItemClicked(movie: MovieModel) {
        view?.showDetailFragment(movie)
    }
}