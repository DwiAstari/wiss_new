package com.dwiastari.wiss.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ListMasyarakat(
    val username: String? = null,
    val image: String? = null,
    val name: String? = null,
    val lastMessage: String? = null,
    val newNotif: Boolean? = null,
    val timeMillis: Long? = null
)