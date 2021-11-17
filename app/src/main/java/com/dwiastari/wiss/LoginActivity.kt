package com.dwiastari.wiss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //insialisasi button admin
        val mStartActBtn = findViewById<Button>(R.id.masukbtn)
        //pemanggilan btn admin
        mStartActBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity,DashboardActivity::class.java))
        }

        //sebagai actionbar kembali ke main
        val actionBar = supportActionBar
        //set actionbar
        actionBar!!.title = "Keluargaku"
        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}