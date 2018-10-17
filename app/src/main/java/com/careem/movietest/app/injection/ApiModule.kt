package com.careem.movietest.app.injection

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.careem.movietest.app.BuildConfig
import com.careem.movietest.app.api.MovieApi
import com.careem.movietest.app.base.MasterApplication
import com.careem.movietest.app.base.PublicConstant
import com.careem.movietest.app.extension.isConnected
import com.careem.movietest.app.model.exception.NoNetworkException
import com.careem.movietest.app.model.exception.WebApiException
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLPeerUnverifiedException

@Module
class ApiModule {

    private fun getOkHttpBuilder(): OkHttpClient.Builder =
            OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor()
                            .apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }
                    )

    @Provides
    @Singleton
    fun providesOkHttpClient(app: MasterApplication): OkHttpClient {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return getOkHttpBuilder()
                .cache(Cache(app.cacheDir,10*1024*1024))
                .connectTimeout(7, TimeUnit.SECONDS)
                .readTimeout(7, TimeUnit.SECONDS)
                .writeTimeout(7, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()

                    if(!connectivityManager.isConnected)
                        throw NoNetworkException()

                    try {
                        val response =  chain.proceed(requestBuilder.build())

                        if(response.isSuccessful)
                            response
                        else {
                            val responseBody = response.body()?.string() ?: ""
                            val pair = getErrorMsg(responseBody)
                            throw WebApiException(pair.first,pair.second)
                        }
                    } catch (e: SocketTimeoutException) {
                        throw NoNetworkException()
                    }  catch (e: ConnectException) {
                        throw NoNetworkException()
                    } catch (e: UnknownHostException) {
                        throw WebApiException("Unknown Host",-4)
                    } catch (e: SSLPeerUnverifiedException) {
                        throw WebApiException("SSL Error",-3)
                    }
                }
                .build()
    }

    private fun getErrorMsg(responseBody: String): Pair<String,Int> {
        try {
            if (responseBody == "") {
                return Pair("Error in calling the web api",-2)
            } else {
                val jsonError = JSONObject(responseBody)
                val errorString = jsonError.getString("status_message")
                val errorCode = jsonError.getInt("status_code")

                return Pair(errorString,errorCode)
            }
        }catch (exc:Exception){
            return Pair("Problem in parsing the error response",-1)
        }
    }

    private fun makeDefaultRetrofit(okHttpClient: OkHttpClient, gson: Gson) : Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(PublicConstant.BaseUrl)
                .client(okHttpClient)
                .build()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return makeDefaultRetrofit(okHttpClient,gson)
    }

    @Provides
    @Singleton
    fun providesProductApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}