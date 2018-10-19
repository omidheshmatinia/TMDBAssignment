package com.careem.movietest.app.fragment.movielist

import com.careem.movietest.app.MasterTest
import com.careem.movietest.app.interfaces.NoConnectionInterface
import com.careem.movietest.app.model.MovieModel
import com.careem.movietest.app.model.api.MovieListApiResponse
import com.careem.movietest.app.model.exception.NoNetworkException
import com.careem.movietest.app.repository.MovieRepository
import com.careem.movietest.app.util.ResourceManager
import io.reactivex.Observable
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.*
import io.reactivex.schedulers.Schedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.android.plugins.RxAndroidPlugins
import java.lang.Exception


@RunWith(MockitoJUnitRunner::class)
class MovieListFPresenterTest : MasterTest() {

    @Mock
    lateinit var movieRepository:MovieRepository
    @Mock
    lateinit var resManager:ResourceManager
    @Mock
    lateinit var view:MovieListFContract.View
    @Mock
    lateinit var presenter:MovieListFPresenter

    private val size = 20L
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter =  MovieListFPresenter(movieRepository,resManager)
        // for rx java schedulers
        fixRxJavaSchedulers()
        // for repository
        doReturn(Observable.just(makeSampleResponse(1,20,200,size))).`when`(movieRepository).getRecentMoviesFromServer(ArgumentMatchers.anyInt())
        doReturn(Observable.just(makeSampleResponse(1,20,200,size))).`when`(movieRepository).getRecentMoviesFilteredByDateFromServer(ArgumentMatchers.anyInt(), any(), any())
    }

    private fun makeSampleResponse(page:Long,tPage:Long,tResult:Long,size: Long):MovieListApiResponse{
        return MovieListApiResponse(page,tPage,tResult,makeSampleMovieList(size))
    }

    private fun makeSampleMovieList(size:Long):List<MovieModel>{
        val items = arrayListOf<MovieModel>()
        (1..size).map{
            items.add(MovieModel(0,0.0,"","","2018-10-10",it,0.0,false,false,"Item$it","Overview$it"))
        }
        return items
    }

    @Test
    fun setupListCalledWhenViewAttached(){
        presenter.attachView(view)
        Mockito.verify(view, Mockito.times(1))?.setupList(ArgumentMatchers.anyList(), any(Int::class.java))
    }

    @Test
    fun setupListCalledWithColumnSizeFromResManager(){
        `when`(resManager.getColumnSize()).thenReturn(3)
        presenter.attachView(view)
        Mockito.verify(view, Mockito.times(1))?.setupList(ArgumentMatchers.anyList(), eq(3))
    }

    @Test
    fun showAndHideCenterProgressForApiCall(){
        presenter.attachView(view)
        Mockito.verify(view, Mockito.times(1))?.showProgress(MovieListFContract.ProgressType.CenterProgressBar)
        Mockito.verify(view, Mockito.times(1))?.hideProgress(MovieListFContract.ProgressType.CenterProgressBar)
    }

    @Test
    fun addNewItemToListAfterResponseReceived(){
        presenter.attachView(view)
        Mockito.verify(view, Mockito.times(1))?.addNewItemsToList(0,size.toInt())
    }

    @Test
    fun showNoNetworkConnectionDialogOnConnectionError(){
        doReturn(Observable.error<NoNetworkException>(NoNetworkException())).`when`(movieRepository).getRecentMoviesFromServer(ArgumentMatchers.anyInt())
        presenter.attachView(view)
        Mockito.verify(view, Mockito.times(1))?.showNoConnectionDialog(any())
        Mockito.verify(view, Mockito.times(0))?.showRetrySnack(ArgumentMatchers.anyString(), any())
    }

    @Test
    fun showRetrySnackOnError(){
        doReturn(Observable.error<Exception>(Exception())).`when`(movieRepository).getRecentMoviesFromServer(ArgumentMatchers.anyInt())
        presenter.attachView(view)
        Mockito.verify(view, Mockito.times(0))?.showNoConnectionDialog(any())
        Mockito.verify(view, Mockito.times(1))?.showRetrySnack(ArgumentMatchers.anyString(), any())
    }

    @Test
    fun showAndHideBottomProgressOnScroll(){
        presenter.attachView(view)
        presenter.movieListReachEnd()
        Mockito.verify(view,Mockito.times(1))?.showProgress(MovieListFContract.ProgressType.BottomProgressbar)
        Mockito.verify(view,Mockito.times(2))?.hideProgress(MovieListFContract.ProgressType.BottomProgressbar)
    }

    @Test
    fun showBottomSheetDialogOnFabClick(){
        presenter.attachView(view)
        presenter.buttonDateRangeClicked()
        Mockito.verify(view,Mockito.times(1)).showDateChoosingBottomSheet()
    }

    @Test
    fun show(){
        presenter.attachView(view)
        presenter.buttonDateRangeClicked()
        Mockito.verify(view,Mockito.times(1)).showDateChoosingBottomSheet()
    }

    @Test
    fun clearItemsOnNewDateSearch(){
        assertEquals(presenter.pageIndex,1)
        presenter.attachView(view)
        assertEquals(presenter.pageIndex,2)
        Mockito.verify(view,Mockito.times(1)).showDateChoosingBottomSheet()
    }

    @Test
    fun viewInitializedOnAttachView(){
        assertNull(presenter.view)
        presenter.attachView(view)
        assertNotNull(presenter.view)
    }

    @Test
    fun viewBecomeNullOnDestroy(){
        presenter.attachView(view)
        presenter.destroy()
        assertNull(presenter.view)
    }

    @Test
    fun allDisposablesDisposeOnDestroy(){
        presenter.attachView(view)
        assertFalse(presenter.compositeDisposable.isDisposed)
        presenter.destroy()
        assertTrue(presenter.compositeDisposable.isDisposed)
    }


    @After
    fun tearDown() {
    }
}