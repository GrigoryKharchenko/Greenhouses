package com.greenhouses.data.response

import com.google.gson.annotations.SerializedName

data class ProfileDataResponse(
    @SerializedName("profile_data") val profileResponse: ProfileResponse
)

data class ProfileResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("phone") val phone: String,
)
