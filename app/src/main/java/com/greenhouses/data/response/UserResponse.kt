package com.greenhouses.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("user_id") val userId: Int,
)
