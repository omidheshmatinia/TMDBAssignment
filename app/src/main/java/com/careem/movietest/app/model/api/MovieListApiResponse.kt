package com.careem.movietest.app.model.api

import com.careem.movietest.app.model.MovieModel
import com.google.gson.annotations.SerializedName


data class MovieListApiResponse(
        var page:Int,
        @SerializedName("total_results") var totalResults:Long,
        @SerializedName("total_pages") var totalPages:Long,
        var results:List<MovieModel>
):MasterApiResponse()