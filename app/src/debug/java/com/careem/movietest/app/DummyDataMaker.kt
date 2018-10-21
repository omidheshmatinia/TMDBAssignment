package com.careem.movietest.app

import com.careem.movietest.app.model.MovieModel
import com.careem.movietest.app.model.api.MovieListApiResponse


class DummyDataMaker {

    companion object {
        val SAMPLE_MOVIE_POSTERPATH = "pathP"
        val SAMPLE_MOVIE_BACKDROPPATH = "backdropP"
        val SAMPLE_MOVIE_RELEASEDATE = "2018-10-10"
        val SAMPLE_MOVIE_TITLE = "TestMovieTitle"
        val SAMPLE_MOVIE_OVERVIEW = "TestMovieOverView"
        val SAMPLE_MOVIE_ID = 20L
        val SAMPLE_MOVIE_VOTEAVERAGE = 5.2
        val SAMPLE_MOVIE_VOTECOUNT = 2365L

        fun makeMovieModel():MovieModel{
            return MovieModel(SAMPLE_MOVIE_VOTECOUNT,SAMPLE_MOVIE_VOTEAVERAGE,SAMPLE_MOVIE_POSTERPATH,SAMPLE_MOVIE_BACKDROPPATH,SAMPLE_MOVIE_RELEASEDATE,SAMPLE_MOVIE_ID,0.0,false,false, SAMPLE_MOVIE_TITLE,SAMPLE_MOVIE_OVERVIEW)
        }

        fun makeSampleResponse(page:Long,tPage:Long,tResult:Long,size: Long): MovieListApiResponse {
            return MovieListApiResponse(page,tPage,tResult,makeSampleMovieList(size))
        }

        private fun makeSampleMovieList(size:Long):List<MovieModel>{
            val items = arrayListOf<MovieModel>()
            (1..size).map{
                items.add(MovieModel(0,0.0,"","","2018-10-10",it,0.0,false,false,"Item$it","Overview$it"))
            }
            return items
        }
    }
}