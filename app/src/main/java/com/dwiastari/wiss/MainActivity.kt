package com.dwiastari.wiss

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.dwiastari.wiss.adapter.OnBoardingViewPagerAdapter
import com.dwiastari.wiss.model.OnBoardingData
import com.dwiastari.wiss.ui.admin.login.LoginActivity
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();
        setContentView(R.layout.activity_main)
        //inisialisasi button login
        val mStartActBtn1 = findViewById<Button>(R.id.Masuk)
        //pemanggilan btn login
        mStartActBtn1.setOnClickListener {
            startActivity(Intent( this@MainActivity, LoginActivity::class.java))
        }
        //insialisasi button daftar
        val mStartActBtn = findViewById<Button>(R.id.Daftar)
        //pemanggilan btn daftar
        mStartActBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity,DaftarActivity::class.java))
        }

    }
}