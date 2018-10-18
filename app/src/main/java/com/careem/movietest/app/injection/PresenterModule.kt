package com.careem.movietest.app.injection

import com.careem.movietest.app.activity.main.MainContract
import com.careem.movietest.app.activity.main.MainPresenter
import com.careem.movietest.app.fragment.moviedetail.MovieDetailFContract
import com.careem.movietest.app.fragment.moviedetail.MovieDetailFPresenter
import com.careem.movietest.app.fragment.movielist.MovieListFContract
import com.careem.movietest.app.fragment.movielist.MovieListFPresenter
import com.careem.movietest.app.repository.MovieRepository
import com.careem.movietest.app.util.ResourceManager
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun provideMovieListFPresenter(mRep:MovieRepository,rMan: ResourceManager):MovieListFContract.Presenter = MovieListFPresenter(mRep,rMan)

    @Provides
    fun provideMovieDetailFPresenter(mRep:MovieRepository): MovieDetailFContract.Presenter = MovieDetailFPresenter(mRep)

    @Provides
    fun provideMainActivityPresenter(): MainContract.Presenter = MainPresenter()

}