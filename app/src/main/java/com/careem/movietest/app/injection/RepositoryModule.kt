package com.careem.movietest.app.injection

import com.careem.movietest.app.api.MovieApi
import com.careem.movietest.app.base.PublicConstant
import com.careem.movietest.app.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(mApi:MovieApi):MovieRepository = MovieRepository(mApi,PublicConstant.ApiToken)
}
