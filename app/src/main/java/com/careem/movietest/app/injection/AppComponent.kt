package com.careem.movietest.app.injection

import com.careem.movietest.app.activity.MainActivity
import com.careem.movietest.app.fragment.movielist.MovieListFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(AppModule::class),(PresenterModule::class), (RepositoryModule::class), (ApiModule::class)])
interface AppComponent {
    // Activity
    fun inject(activity: MainActivity)

    // Fragment
    fun inject(fragment: MovieListFragment)

}