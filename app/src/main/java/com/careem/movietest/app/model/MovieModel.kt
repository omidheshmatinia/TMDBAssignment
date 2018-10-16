package com.careem.movietest.app.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
        @SerializedName("vote_count") var voteCount:Long,
        @SerializedName("vote_average") var voteAverage:Double,
        @SerializedName("poster_path") var posterPath:String?,
        @SerializedName("backdrop_path") var backdropPath:String?,
        @SerializedName("release_date") var releaseDate:Date,
        var id:Long,
        var popularity:Double,
        var adult:Boolean,
        var video:Boolean,
        var title:String,
        var overview:String
) : Parcelable