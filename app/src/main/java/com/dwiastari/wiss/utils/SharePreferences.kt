package com.dwiastari.wiss.utils

import android.content.Context
import android.preference.PreferenceManager

class SharePreferences (val context: Context){
    companion object {
        private const val FIRST_INSTALL = "FIRST_INSTALL"
    }
    private val p = PreferenceManager.getDefaultSharedPreferences(context)

    var firstInstall = p.getBoolean(FIRST_INSTALL,false)
    set(value) = p.edit().putBoolean(FIRST_INSTALL, value).apply()

}