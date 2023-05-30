package com.greenhouses.data.request

import com.google.gson.annotations.SerializedName

data class PhoneRequest(
    @SerializedName("phone")
    val phone: String,
)
