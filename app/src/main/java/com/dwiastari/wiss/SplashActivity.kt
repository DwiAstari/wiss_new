package com.dwiastari.wiss

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dwiastari.wiss.utils.SharePreferences

class SplashActivity : AppCompatActivity() {
    lateinit var pre: SharePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        pre = SharePreferences(this)

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