package com.dwiastari.wiss.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    val id_video: String,
    val judul_video: String,
    val link_video: String
): Parcelable
