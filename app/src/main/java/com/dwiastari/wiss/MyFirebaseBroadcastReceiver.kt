package com.dwiastari.wiss

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class MyFirebaseBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val intentService = Intent(context, MyFirebaseNotificationService::class.java)
        intentService.putExtra("title", intent?.getStringExtra("title"))
        intentService.putExtra("message", intent?.getStringExtra("message"))
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context!!.startForegroundService(intentService)
        } else {
            context!!.startService(intentService)
        }
    }
}