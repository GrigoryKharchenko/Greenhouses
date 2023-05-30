package com.greenhouses.data.response

import com.google.gson.annotations.SerializedName

data class CodeResponse(
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("is_user_exists") val isUserExists: Boolean,
)
