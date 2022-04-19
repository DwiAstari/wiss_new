package com.dwiastari.wiss.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Slide (
    val id_slide: String,
    val judul_slide: String,
    val foto_slides: String,
    val status: String,
    val no_urut: String
): Parcelable