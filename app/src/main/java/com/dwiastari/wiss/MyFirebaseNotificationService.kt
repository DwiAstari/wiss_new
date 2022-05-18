package com.dwiastari.wiss

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.dwiastari.wiss.ui.masyarakat.pesan.ChatActivity

class MyFirebaseNotificationService: Service() {
    
    override fun onCreate() {
        super.onCreate()
        Log.d("notification", "Service Started")
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, ChatActivity::class.java)
        
        return START_NOT_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    
}