package com.greenhouses.data.request

import com.google.gson.annotations.SerializedName

data class UserUpdatedRequest(
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: String,
    @SerializedName("city") val city: String,
    @SerializedName("birthday") val birthday: String,
    @SerializedName("vk") val vk: String,
    @SerializedName("instagram") val instagram: String,
    @SerializedName("status") val status: String,
    // @SerializedName("avatar") val avatarRequest: AvatarRequest,
)

data class AvatarRequest(
    @SerializedName("filename") val filename: String,
    @SerializedName("base_64") val base64: String
)
