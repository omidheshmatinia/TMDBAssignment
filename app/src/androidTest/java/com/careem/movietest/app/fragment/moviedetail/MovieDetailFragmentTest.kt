package com.careem.movietest.app.fragment.moviedetail

import android.graphics.Point
import android.os.RemoteException
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.careem.movietest.app.activity.main.MainActivity
import com.careem.movietest.app.model.MovieModel
import org.junit.Rule
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import com.careem.movietest.app.R
import org.junit.Before
import org.junit.Test
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import com.careem.movietest.app.repository.MovieRepository
import dagger.Component
import javax.inject.Singleton
import android.support.test.annotation.UiThreadTest
import android.support.test.espresso.matcher.ViewMatchers.*
import com.careem.movietest.app.MasterAndroidTest
import com.careem.movietest.app.TestActivity
import com.careem.movietest.app.base.MasterApplication
import com.careem.movietest.app.injection.*
import com.careem.movietest.app.model.exception.NoNetworkException
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.allOf
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import javax.inject.Inject
import android.view.WindowManager
import android.app.KeyguardManager
import android.content.Context
import android.support.test.espresso.Espresso
import android.support.test.rule.UiThreadTestRule
import org.junit.BeforeClass




@RunWith(AndroidJUnit4::class)
class MovieDetailFragmentTest : MasterAndroidTest() {

    private val posterPath = "pathP"
    private val backdropPath = "backdropP"
    private val releaseDate = "2018-10-10"
    private val title = "TestMovieTitle"
    private val overView = "TestMovieOverView"
    private val id = 20L
    private val voteAverage = 5.2
    private val voteCount = 2365L

    @Singleton
    @Component(modules = [(AppModule::class),(PresenterModule::class), (RepositoryModule::class), (ApiModule::class)])
    interface MockAppComponent : AppComponent{
        fun inject(test: MovieDetailFragmentTest)
    }

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(TestActivity::class.java,true,true)
    @Rule
    @JvmField
    public var uiThreadTestRule: UiThreadTestRule = UiThreadTestRule()

    @Inject
    lateinit var movieRepository:MovieRepository


    fun setUpWakeUp(){
        uiThreadTestRule.runOnUiThread {

            val myKM = activityTestRule.activity.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            val isPhoneLocked = myKM.inKeyguardRestrictedInputMode()

            if (isPhoneLocked) {
                activityTestRule.activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
            }
        }
    }

    @Before
    @UiThreadTest
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app:MasterApplication = instrumentation.targetContext
                .applicationContext as MasterApplication

        val testComponent:MockAppComponent = DaggerMovieDetailFragmentTest_MockAppComponent.builder()
                .appModule(AppModule(MasterApplication.instance))
                .repositoryModule(MockRepositoryModule())
                .build()
        app.setComponent(testComponent)
        testComponent.inject(this)
        // for rx java schedulers
        fixRxJavaSchedulers()
        // for repository
        Mockito.doReturn(Observable.just(makeSampleMovie())).`when`(movieRepository).getMovieDetailFromServer(ArgumentMatchers.anyLong())

        activityTestRule.activity.setFragment(MovieDetailFragment.newInstance(makeSampleMovie()))

        // turn on the screen to avoid exception
//        activityTestRule.activity.runOnUiThread{
//            val km =  activityTestRule.activity.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
//            val keyguardLock = km.newKeyguardLock("TAG")
//            keyguardLock.disableKeyguard()
//            activityTestRule.activity.window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
//        }
    }

    private fun makeSampleMovie(): MovieModel {
        return MovieModel(voteCount,voteAverage,posterPath,backdropPath,releaseDate,id,0.0,false,false,title,overView)
    }

    @Test
    fun showConnectionDialogWhenThereIsConnectionError(){
        Mockito.doReturn(Observable.error<NoNetworkException>(NoNetworkException())).`when`(movieRepository).getMovieDetailFromServer(ArgumentMatchers.anyLong())
// check title is shown
        onView(withId(R.id.button_dialog_noConnection_tryAgain)).check(matches(isDisplayed()))
    }

    @Test
    fun showMovieDetailsOnUIAfterFragmentBuild() {
        // check title is shown
        onView(withId(R.id.textView_movieDetailF_title)).check(matches(isDisplayed()))
        onView(withId(R.id.textView_movieDetailF_title)).check(matches(withText(title)))
        // check overview is shown
        onView(withId(R.id.textView_movieDetailF_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.textView_movieDetailF_overview)).check(matches(withText(overView)))
        // check averageRate is shown
        onView(withId(R.id.textView_movieDetailF_averageRate)).check(matches(isDisplayed()))
        onView(withId(R.id.textView_movieDetailF_averageRate)).check(matches(withText("Average Rate : $voteAverage / 10 ($voteCount votes)")))
        // check averageRate is shown
        onView(withId(R.id.textView_movieDetailF_releaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.textView_movieDetailF_releaseDate)).check(matches(withText("Release Date : $releaseDate")))
    }

}