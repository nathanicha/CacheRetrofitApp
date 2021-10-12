package com.natlwd.cacheretrofitapp.remote

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class OfflineCacheInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // prevent caching when network is on. For that we use the "onlineCachingInterceptor"
        if (!isConnected()) {
            Log.d("CachingRetrofit", "Offline Interceptor")

            //If cache is less or equal 7 days, It will using it
            val cacheControl: CacheControl = CacheControl.Builder()
                .maxStale(7, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .cacheControl(cacheControl)
                .build()
        }

        return chain.proceed(request)
    }

    private fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}