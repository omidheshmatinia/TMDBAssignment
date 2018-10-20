package com.careem.movietest.app.injection

import com.careem.movietest.app.api.MovieApi
import com.careem.movietest.app.base.PublicConstant
import com.careem.movietest.app.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
open class RepositoryModule {

    @Provides
    @Singleton
    open fun provideMovieRepository(mApi:MovieApi):MovieRepository = MovieRepository(mApi,PublicConstant.ApiToken)
}
