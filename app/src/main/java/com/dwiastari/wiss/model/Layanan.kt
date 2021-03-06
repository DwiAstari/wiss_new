package com.dwiastari.wiss.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Layanan(
    val id_hari: String,
    val hari: String,
    val layanan: String
): Parcelable