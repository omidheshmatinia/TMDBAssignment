package com.careem.movietest.app.fragment.MovieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.careem.movietest.app.R
import com.careem.movietest.app.base.MasterApplication
import com.careem.movietest.app.base.MasterFragment
import javax.inject.Inject


class MovieListFragment : MasterFragment(), MovieListFContract.View {

    @Inject
    lateinit var presenter: MovieListFContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MasterApplication.appComponent.inject(this)
        presenter.attachView(this)
    }

    override fun onDetach() {
        presenter.destroy()
        super.onDetach()
    }
}