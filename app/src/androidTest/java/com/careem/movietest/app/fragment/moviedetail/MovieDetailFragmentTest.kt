package com.careem.movietest.app.fragment.moviedetail

import android.graphics.Point
import android.os.RemoteException
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.careem.movietest.app.activity.main.MainActivity
import com.careem.movietest.app.model.MovieModel
import org.junit.Rule
import org.junit.runner.RunWith
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.careem.movietest.app.R
import org.junit.Before
import org.junit.Test
import android.support.test.InstrumentationRegistry




@RunWith(AndroidJUnit4::class)
class MovieDetailFragmentTest{

    private val posterPath = "pathP"
    private val backdropPath = "backdropP"
    private val releaseDate = "2018-10-10"
    private val title = "TestMovieTitle"
    private val overView = "TestMovieOverView"
    private val id = 20L
    private val voteAverage = 5.2
    private val voteCount = 2365L

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    private fun makeSampleMovie(): MovieModel {
        return MovieModel(voteCount,voteAverage,posterPath,backdropPath,releaseDate,id,0.0,false,false,title,overView)
    }

    private fun startDetailFragment(): MovieDetailFragment {
        val activity = activityTestRule.activity as MainActivity
        val transaction = activity.supportFragmentManager.beginTransaction()
        val fragment = MovieDetailFragment.newInstance(makeSampleMovie())
        transaction.add(R.id.fragment_mainActivity,fragment, "detail")
        transaction.commitAllowingStateLoss()
        return fragment
    }

    @Before
    fun addFragment(){

        activityTestRule.activity.runOnUiThread {
            startDetailFragment()
        }
        Thread.sleep(200)
    }

    @Test
    fun fragment_can_be_instantiated() {
        // Then use Espresso to test the Fragment
        onView(withId(R.id.imageView_dialog_noConnection_cancel)).check(matches(isDisplayed()))
    }

}