package com.natlwd.cacheretrofitapp.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null
)