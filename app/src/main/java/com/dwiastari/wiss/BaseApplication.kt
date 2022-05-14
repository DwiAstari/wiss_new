package com.dwiastari.wiss

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application(){
    init {
        if(BuildConfig.DEBUG){
//            StrictMode.enableDefaults()
        }
    }
}