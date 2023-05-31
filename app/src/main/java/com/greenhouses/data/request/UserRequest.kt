package com.greenhouses.data.request

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
)
