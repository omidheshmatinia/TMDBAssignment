package com.careem.movietest.app.model.exception

import java.lang.Exception


class WebApiException(var errorMessage:String,var statusCode:Int):Exception(errorMessage)