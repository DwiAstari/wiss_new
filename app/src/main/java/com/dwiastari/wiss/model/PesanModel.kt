package com.dwiastari.wiss.model

data class PesanModel (
    val image: String,
    val text1: String,
    val text2: String,
    val username: String,
    val newNotif: Boolean,
    val timeInMillis: Long
        )