package com.greenhouses.presentation.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

fun Bitmap.encodeToBase64(): String {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 0, stream)
    return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP)
}

fun String.decodeToBitmap(): Bitmap? {

    return try {
        val byteArray = Base64.decode(this, Base64.NO_WRAP)
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    } catch (e: Exception) {
        null
    }
}
