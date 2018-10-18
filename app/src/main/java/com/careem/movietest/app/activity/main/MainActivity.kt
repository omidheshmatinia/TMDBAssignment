package com.careem.movietest.app.activity.main

import android.os.Bundle
import com.careem.movietest.app.R
import com.careem.movietest.app.base.MasterActivity
import com.careem.movietest.app.base.MasterApplication
import com.careem.movietest.app.fragment.movielist.MovieListFragment
import javax.inject.Inject

class MainActivity : MasterActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MasterApplication.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        if(lastCustomNonConfigurationInstance==null){
            MasterApplication.appComponent.inject(this)
            presenter.attachView(this)
        } else {
            presenter = lastCustomNonConfigurationInstance as MainContract.Presenter
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return presenter
    }

    override fun showMovieListFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.animator.fade_in,R.animator.fade_out,R.animator.fade_in,R.animator.fade_out)
        transaction.add(R.id.fragment_mainActivity, MovieListFragment())
        transaction.addToBackStack("movieList")
        transaction.commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>1)
            supportFragmentManager.popBackStack()
        else
            finish()
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

}

