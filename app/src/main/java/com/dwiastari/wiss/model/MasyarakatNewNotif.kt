package com.dwiastari.wiss.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class MasyarakatNewNotif (
    val sender: String? = null,
    val newNotif: Boolean? = null
    )