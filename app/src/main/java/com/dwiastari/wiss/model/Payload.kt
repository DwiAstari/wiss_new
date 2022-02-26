package com.dwiastari.wiss.model


import com.google.gson.annotations.SerializedName
import android.os.Parcelable

data class Payload(
    @SerializedName("nama_user")
    val namaUser: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String
)