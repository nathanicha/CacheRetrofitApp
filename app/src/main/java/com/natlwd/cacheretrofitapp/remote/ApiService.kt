package com.natlwd.cacheretrofitapp.remote

import com.natlwd.cacheretrofitapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/todos/{userId}")
    fun getUser(
            @Path("userId") userId: String
    ): Call<UserResponse?>
}