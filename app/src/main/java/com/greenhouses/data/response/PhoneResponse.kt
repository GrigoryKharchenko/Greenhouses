package com.greenhouses.data.response

import com.google.gson.annotations.SerializedName

data class PhoneResponse(
    @SerializedName("is_success") val isSuccess: Boolean
)
