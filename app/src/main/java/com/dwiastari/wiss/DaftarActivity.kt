package com.dwiastari.wiss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DaftarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)

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