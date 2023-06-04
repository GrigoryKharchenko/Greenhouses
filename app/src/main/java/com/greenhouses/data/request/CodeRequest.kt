package com.greenhouses.data.request

import com.google.gson.annotations.SerializedName

data class CodeRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("code") val code: String,
)
