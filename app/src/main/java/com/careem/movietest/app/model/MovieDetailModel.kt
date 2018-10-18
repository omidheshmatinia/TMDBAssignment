package com.careem.movietest.app.model

data class MovieDetailModel(
        var tagline:String,
        var status:String,
        var revenue:Long,
        var runtime:Int,
        var popularity:Double,
        var budget:Long
)