package com.careem.movietest.app.fragment.moviedetail

import com.careem.movietest.app.MasterTest
import com.careem.movietest.app.base.PublicConstant
import com.careem.movietest.app.model.MovieDetailModel
import com.careem.movietest.app.model.MovieModel
import com.careem.movietest.app.model.exception.NoNetworkException
import com.careem.movietest.app.repository.MovieRepository
import io.reactivex.Observable
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailFPresenterTest:MasterTest() {

    @Mock
    lateinit var movieRepository: MovieRepository
    @Mock
    lateinit var view:MovieDetailFContract.View
    @Mock
    lateinit var presenter:MovieDetailFPresenter

    private val tagLine = "tagLine"
    private val status = "status"
    private val posterPath = "pathP"
    private val backdropPath = "backdropP"
    private val releaseDate = "2018-10-10"
    private val title = "TestMovieTitle"
    private val overView = "TestMovieOverView"
    private val id = 20L
    private val voteAverage = 5.2
    private val voteCount = 2365L
    private val budget = 1200L
    private val runtime = 100
    private val revenue = 1000L

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter =  MovieDetailFPresenter(movieRepository)
        // for rx java schedulers
        fixRxJavaSchedulers()
        // for repository
        Mockito.doReturn(Observable.just(makeSampleResponse())).`when`(movieRepository).getMovieDetailFromServer(ArgumentMatchers.anyLong())
    }

    private fun makeSampleResponse():MovieDetailModel{
        return MovieDetailModel(tagLine,status,revenue,runtime,2.1,budget)
    }

    private fun makeSampleMovie():MovieModel{
        return MovieModel(voteCount,voteAverage,posterPath,backdropPath,releaseDate,id,0.0,false,false,title,overView)
    }

    @Test(expected = Exception::class)
    fun throwExceptionWhenCallAttachViewWithoutSetMovieFromIntent(){
        presenter.attachView(view)
    }

    @Test
    fun getMovieDetailFromServerOnViewAttached(){
        presenter.setMovieFromIntent(makeSampleMovie())
        presenter.attachView(view)
        verify(movieRepository,Mockito.times(1)).getMovieDetailFromServer(presenter.movie.id)
    }

    @Test
    fun setMovieInformationOnViewOnViewAttached(){
        presenter.setMovieFromIntent(makeSampleMovie())
        presenter.attachView(view)
        verify(view,Mockito.times(1)).showPosterImage(PublicConstant.ImagePath+posterPath)
        verify(view,Mockito.times(1)).showBackDropImage(PublicConstant.ImagePath+backdropPath)
        verify(view,Mockito.times(1)).setText(title,MovieDetailFContract.TextViewType.Title)
        verify(view,Mockito.times(1)).setText(overView,MovieDetailFContract.TextViewType.Description)
        verify(view,Mockito.times(1)).setText("Release Date : $releaseDate",MovieDetailFContract.TextViewType.ReleaseDate)
        verify(view,Mockito.times(1)).setText("Average Rate : $voteAverage / 10 ($voteCount votes)",MovieDetailFContract.TextViewType.VoteAverage)
    }

    @Test
    fun showMoreDetailOnScreenAfterResponseReceived(){
        presenter.setMovieFromIntent(makeSampleMovie())
        presenter.attachView(view)
        Mockito.verify(view, Mockito.times(1))?.showView(MovieDetailFContract.ViewType.DetailProgress)
        Mockito.verify(view, Mockito.times(1))?.hideView(MovieDetailFContract.ViewType.DetailProgress)
        Mockito.verify(view, Mockito.times(1))?.showView(MovieDetailFContract.ViewType.CardViewRunTime)
        Mockito.verify(view, Mockito.times(1))?.showView(MovieDetailFContract.ViewType.CardViewBudget)
        Mockito.verify(view, Mockito.times(1))?.showView(MovieDetailFContract.ViewType.CardViewRevenue)
        verify(view,Mockito.times(1)).setText("RunTime : $runtime minutes",MovieDetailFContract.TextViewType.Runtime)
        verify(view,Mockito.times(1)).setText("Budget : $budget",MovieDetailFContract.TextViewType.Budget)
        verify(view,Mockito.times(1)).setText("Revenue : $revenue",MovieDetailFContract.TextViewType.Revenue)
    }

    @Test
    fun showNoNetworkConnectionDialogOnConnectionError(){
        Mockito.doReturn(Observable.error<NoNetworkException>(NoNetworkException())).`when`(movieRepository).getMovieDetailFromServer(ArgumentMatchers.anyLong())
        presenter.setMovieFromIntent(makeSampleMovie())
        presenter.attachView(view)
        Mockito.verify(view, Mockito.times(1))?.showNoConnectionDialog(any())
        Mockito.verify(view, Mockito.times(0))?.showRetrySnack(ArgumentMatchers.anyString(), any())
    }

    @Test
    fun showRetrySnackOnError(){
        Mockito.doReturn(Observable.error<java.lang.Exception>(java.lang.Exception())).`when`(movieRepository).getMovieDetailFromServer(ArgumentMatchers.anyLong())
        presenter.setMovieFromIntent(makeSampleMovie())
        presenter.attachView(view)
        Mockito.verify(view, Mockito.times(0))?.showNoConnectionDialog(any())
        Mockito.verify(view, Mockito.times(1))?.showRetrySnack(ArgumentMatchers.anyString(), any())
    }

    @Test
    fun viewInitializedOnAttachView(){
        presenter.setMovieFromIntent(makeSampleMovie())
        assertNull(presenter.view)
        presenter.attachView(view)
        assertNotNull(presenter.view)
    }

    @Test
    fun viewBecomeNullOnDestroy(){
        presenter.setMovieFromIntent(makeSampleMovie())
        presenter.attachView(view)
        presenter.destroy()
        assertNull(presenter.view)
    }

    @Test
    fun allDisposablesDisposeOnDestroy(){
        presenter.setMovieFromIntent(makeSampleMovie())
        presenter.attachView(view)
        assertFalse(presenter.compositeDisposable.isDisposed)
        presenter.destroy()
        assertTrue(presenter.compositeDisposable.isDisposed)
    }

}