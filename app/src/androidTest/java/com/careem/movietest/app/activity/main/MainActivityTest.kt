package com.careem.movietest.app.activity.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.careem.movietest.app.R
import com.careem.movietest.app.fragment.movielist.MovieListFragment
import org.junit.*

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun fragmentIsAddedToSupportFragmentManager(){
        val fragmentManager = activityTestRule.activity.fragmentManager
        Assert.assertEquals(0,fragmentManager.backStackEntryCount)
        val supportFragmentManager = activityTestRule.activity.supportFragmentManager
        Assert.assertEquals(1,supportFragmentManager.backStackEntryCount)
    }

    @Test
    fun movieListFragmentIsShown(){
        val supportFragmentManager = activityTestRule.activity.supportFragmentManager
        Assert.assertTrue(supportFragmentManager.fragments[0] is MovieListFragment)
    }

}