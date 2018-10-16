package com.careem.movietest.app.repository

import com.careem.movietest.app.api.MovieApi
import com.careem.movietest.app.model.api.MovieListApiResponse
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*

class MovieRepository(private var movieApi:MovieApi,private var apiToken:String) {

    fun getRecentMoviesFromServer(page:Int):Observable<MovieListApiResponse>{
        val queryParams = mutableMapOf<String,String>()
        queryParams["api_key"] = apiToken
        queryParams["page"] = page.toString()
        queryParams["sort_by"] = "primary_release_date.desc"
        queryParams["release_date.lte"] = convertDateToString(Calendar.getInstance().time)
        return movieApi.discover(queryParams)
    }

    fun getRecentMoviesFilteredByDateFromServer(page:Int,start: Date?, end:Date?): Observable<MovieListApiResponse> {
        val queryParams = mutableMapOf<String,String>()
        queryParams["api_key"] = apiToken
        queryParams["page"] = page.toString()
        start?.run { queryParams["release_date.lte"] = convertDateToString(start)  }
        end?.run { queryParams["release_date.gte"] = convertDateToString(end)  }
        return movieApi.discover(queryParams)
    }

    fun getMovieDetailFromServer(movieId:Long):Any{
        return movieApi.getDetail(movieId,apiToken)
    }

    private fun convertDateToString(date:Date):String{
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return format.format(date)
    }
}