package com.dwiastari.wiss.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ebook(
    val id_ebook: String,
    val judul_ebook: String,
    val link_ebook: String
): Parcelable
