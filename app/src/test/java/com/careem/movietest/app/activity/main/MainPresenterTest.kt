package com.careem.movietest.app.activity.main

import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    private lateinit var view: MainContract.View

    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter =  MainPresenter()
    }

    @Test
    fun showMovieListFragmentInAttachView(){
        presenter.attachView(view)
        verify(view,times(1))?.showMovieListFragment()
    }

    @Test
    fun viewInitializedOnAttachView(){
        assertNull(presenter.view)
        presenter.attachView(view)
        assertNotNull(presenter.view)
    }

    @Test
    fun viewBecomeNullOnDestroy(){
        presenter.attachView(view)
        presenter.destroy()
        assertNull(presenter.view)
    }

    @Test
    fun allDisposablesDisposeOnDestroy(){
        presenter.attachView(view)
        assertFalse(presenter.compositeDisposable.isDisposed)
        presenter.destroy()
        assertTrue(presenter.compositeDisposable.isDisposed)
    }

}