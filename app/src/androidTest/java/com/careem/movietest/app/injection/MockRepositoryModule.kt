package com.careem.movietest.app.injection

import com.careem.movietest.app.api.MovieApi
import com.careem.movietest.app.repository.MovieRepository
import org.mockito.Mockito

open class MockRepositoryModule :RepositoryModule() {
    override fun provideMovieRepository(mApi: MovieApi): MovieRepository {
        return Mockito.mock(MovieRepository::class.java)
    }
}