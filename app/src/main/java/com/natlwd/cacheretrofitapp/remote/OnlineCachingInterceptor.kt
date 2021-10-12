package com.natlwd.cacheretrofitapp.remote

import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class OnlineCachingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e("CachingRetrofit", "Online Interceptor")

        val response = chain.proceed(chain.request())

        Log.e("CachingRetrofit", "status code: ${response.code}")

        //Cache is working only GET Method
        return when (response.code) {
            in 200..299, HttpURLConnection.HTTP_NOT_MODIFIED -> {
                val cacheControl: CacheControl = CacheControl.Builder()
                        .maxAge(1, TimeUnit.MINUTES)
                        .build()

                response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheControl.toString())
                        .build()
            }
            else -> {
                //Response not success so cache wouldn't collect
                val cacheControl: CacheControl = CacheControl.Builder()
                        .noStore()
                        .build()

                response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheControl.toString())
                        .build()
            }
        }
    }
}