package com.careem.movietest.app.injection

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
}