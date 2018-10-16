package com.careem.movietest.app.api

import com.careem.movietest.app.model.api.MovieListApiResponse
import io.reactivex.Observable
import retrofit2.http.*


interface MovieApi {

    @GET("discover/movie")
    fun discover(@QueryMap(encoded = false) options:Map<String, String>): Observable<MovieListApiResponse>

    @GET("movie/{movie_id}")
    fun getDetail(@Path("movie_id") movieId:Long,@Query("api_key") token:String): Observable<Any>

}