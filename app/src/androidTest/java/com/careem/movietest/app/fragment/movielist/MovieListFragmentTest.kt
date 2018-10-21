package com.careem.movietest.app.fragment.movielist

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import com.careem.movietest.app.MasterAndroidTest
import com.careem.movietest.app.R
import com.careem.movietest.app.TestActivity
import com.careem.movietest.app.activity.main.MainActivity
import com.careem.movietest.app.base.MasterApplication
import com.careem.movietest.app.fragment.moviedetail.DaggerMovieDetailFragmentTest_MockAppComponent
import com.careem.movietest.app.fragment.moviedetail.MovieDetailFragment
import com.careem.movietest.app.fragment.moviedetail.MovieDetailFragmentTest
import com.careem.movietest.app.injection.*
import com.careem.movietest.app.model.MovieModel
import com.careem.movietest.app.model.api.MovieListApiResponse
import com.careem.movietest.app.model.exception.NoNetworkException
import com.careem.movietest.app.repository.MovieRepository
import dagger.Component
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import javax.inject.Inject
import javax.inject.Singleton

class MovieListFragmentTest: MasterAndroidTest(){

    @Singleton
    @Component(modules = [(AppModule::class),(PresenterModule::class), (RepositoryModule::class), (ApiModule::class)])
    interface MockAppComponent : AppComponent {
        fun inject(test: MovieListFragmentTest)
    }

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java,true,true)

    @Inject
    lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app: MasterApplication = instrumentation.targetContext
                .applicationContext as MasterApplication

        val testComponent:MockAppComponent = DaggerMovieListFragmentTest_MockAppComponent.builder()
                .appModule(AppModule(MasterApplication.instance))
                .repositoryModule(MockRepositoryModule())
                .build()
        app.setComponent(testComponent)
        testComponent.inject(this)
        // for rx java schedulers
        fixRxJavaSchedulers()
        // for repository
        Mockito.doReturn(Observable.just(makeSampleResponse(1, 20, 200, 20))).`when`(movieRepository).getRecentMoviesFromServer(ArgumentMatchers.anyInt())
        Mockito.doReturn(Observable.just(makeSampleResponse(1, 20, 200, 20))).`when`(movieRepository).getRecentMoviesFilteredByDateFromServer(ArgumentMatchers.anyInt(), any(), any())

    }

    private fun makeSampleResponse(page:Long,tPage:Long,tResult:Long,size: Long): MovieListApiResponse {
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
    fun showConnectionDialogWhenThereIsConnectionError(){
        Mockito.doReturn(Observable.error<NoNetworkException>(NoNetworkException())).`when`(movieRepository).getRecentMoviesFromServer(ArgumentMatchers.anyInt())
        Espresso.onView(ViewMatchers.withId(R.id.button_dialog_noConnection_tryAgain)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}