package com.dwiastari.wiss.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Konselor(
    val id_akun: String,
    val nama_akun: String,
    val bidang_konselor: String,
    val email: String,
    val username: String,
    val foto: String

    ): Parcelable