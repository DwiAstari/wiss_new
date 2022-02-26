package com.dwiastari.wiss.model


import com.google.gson.annotations.SerializedName
import android.os.Parcelable

data class LoginAdminResponse(
    @SerializedName("response")
    val response: Boolean,
    @SerializedName("payload")
    val payload: Payload
)