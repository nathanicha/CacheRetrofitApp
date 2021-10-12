package com.natlwd.cacheretrofitapp.remote

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/*
*  from example: https://www.journaldev.com/13639/retrofit-android-example-tutorial
*/
class ApiClient(private val context: Context) {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        //Cache Directory
        val httpCacheDirectory = File(context.cacheDir, "responses")
        //Cache Size
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        val client: OkHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(ChuckInterceptor(context))
                .addNetworkInterceptor(OnlineCachingInterceptor())
                .addInterceptor(OfflineCacheInterceptor(context))
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        return retrofit
    }
}