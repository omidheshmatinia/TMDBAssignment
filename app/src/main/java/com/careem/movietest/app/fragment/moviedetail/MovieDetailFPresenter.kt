package com.careem.movietest.app.fragment.moviedetail


import com.careem.movietest.app.base.MasterFragmentPresenter
import com.careem.movietest.app.base.PublicConstant
import com.careem.movietest.app.interfaces.NoConnectionInterface
import com.careem.movietest.app.interfaces.SnackbarActionListener
import com.careem.movietest.app.model.MovieModel
import com.careem.movietest.app.model.exception.NoNetworkException
import com.careem.movietest.app.repository.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.lang.Exception


class MovieDetailFPresenter(var movieRep:MovieRepository) : MasterFragmentPresenter<MovieDetailFContract.View>(), MovieDetailFContract.Presenter {

    lateinit var movie:MovieModel

    override fun attachView(view: MovieDetailFContract.View) {
        super.attachView(view)
        view.setText(movie.title,MovieDetailFContract.TextViewType.Title)
        view.setText(movie.overview,MovieDetailFContract.TextViewType.Description)
        view.setText("Release Date : "+movie.releaseDate,MovieDetailFContract.TextViewType.ReleaseDate)
        view.setText("Average Rate : ${movie.voteAverage} / 10 (${movie.voteCount} votes)",MovieDetailFContract.TextViewType.VoteAverage)
        view.showBackDropImage(PublicConstant.ImagePath+movie.backdropPath)
        view.showPosterImage(PublicConstant.ImagePath+movie.posterPath)
        view.setupScrollAndToolbarStatus()
        getDetailsFromServer(movie.id)
    }

    private fun getDetailsFromServer(movieId: Long) {
        movieRep.getMovieDetailFromServer(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view?.showView(MovieDetailFContract.ViewType.DetailProgress)
                }
                .doFinally {
                    view?.hideView(MovieDetailFContract.ViewType.DetailProgress)
                }
                .subscribe({
                    view?.setText("Revenue : ${it.revenue}",MovieDetailFContract.TextViewType.Revenue)
                    view?.setText("Budget : ${it.budget}",MovieDetailFContract.TextViewType.Budget)
                    view?.setText("RunTime : ${it.runtime} minutes",MovieDetailFContract.TextViewType.Runtime)
                    view?.showView(MovieDetailFContract.ViewType.CardViewBudget)
                    view?.showView(MovieDetailFContract.ViewType.CardViewRevenue)
                    view?.showView(MovieDetailFContract.ViewType.CardViewRunTime)
                },{
                    when(it){
                        is NoNetworkException -> showNoNetworkDialog(it.message?:"Connection Error")
                        else -> showRetrySnackBar(it.message?:"Error")
                    }
                })
                .addTo(compositeDisposable)
    }


    private fun showRetrySnackBar(error: String) {
        view?.showRetrySnack(error,object: SnackbarActionListener {
            override fun actionClicked() {
                getDetailsFromServer(movie.id)
            }
        })
    }

    private fun showNoNetworkDialog(error:String) {
        view?.showNoConnectionDialog(object : NoConnectionInterface {
            override fun tryAgain() {
                getDetailsFromServer(movie.id)
            }
            override fun cancelDialog() {
                showRetrySnackBar(error)
            }
        })
    }

    override fun scrollChanged() {
        view?.changeLayoutsBasedOnScroll()
    }

    override fun setMovieFromIntent(item: MovieModel?) {
        if(item==null)
            throw Exception("Please use newInstance static method to call this fragment")
        movie = item
    }
}