package com.dwiastari.wiss

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dwiastari.wiss.ui.admin.DashboardAdminActivity
import com.dwiastari.wiss.ui.masyarakat.DashboardActivity
import com.dwiastari.wiss.utils.Constant
import com.dwiastari.wiss.utils.SharePreferences

class SplashActivity : AppCompatActivity() {
    lateinit var pre: SharePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        createNotificationChannel()

        pre = SharePreferences(this)
        val preferences = getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)
        if(!preferences.getString(Constant.KEY_USERNAME, "").isNullOrEmpty()){
            val type = preferences.getString(Constant.KEY_TYPE, "")
            type?.let {
                if(it == "admin"){
                    startActivity(Intent(this, DashboardAdminActivity::class.java))
                } else {
                    startActivity(Intent(this, DashboardActivity::class.java))
                }
            }
        } else {
            Handler().postDelayed({
                var i = Intent()
        
                if (!pre.firstInstall) {
                    i = Intent(this, WalkTroughActivity::class.java)
                }else{
                    i = Intent(this, MainActivity::class.java)
                }
                startActivity(i)
                finish()
            }, 3000)
        }

        

    }
    
    
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "wiss"
            val descriptionText = "notificatiton channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("wiss", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}