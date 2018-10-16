package com.careem.movietest.app.injection

import com.careem.movietest.app.fragment.MovieList.MovieListFContract
import com.careem.movietest.app.fragment.MovieList.MovieListFPresenter
import com.careem.movietest.app.repository.MovieRepository
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun provideMovieListFPresenter(mRep:MovieRepository):MovieListFContract.Presenter = MovieListFPresenter(mRep)
}