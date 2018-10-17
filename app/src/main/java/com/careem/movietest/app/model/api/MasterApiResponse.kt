package com.careem.movietest.app.model.api

import com.google.gson.annotations.SerializedName


open class MasterApiResponse(
        @SerializedName("status_code") var statusCode:Int?,
        @SerializedName("status_message") var statusMessage:String?
                             ){
    constructor() : this (-1,"Error Happened")
}