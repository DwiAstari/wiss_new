package com.dwiastari.wiss.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artikel(
    val area: String,
    val foto_kegiatan: String,
    val id_artikel: String,
    val isi_artikel: String,
    val judul_artikel: String,
    val penulis: String,
    val status: String,
    val tanggal_artikel: String
): Parcelable