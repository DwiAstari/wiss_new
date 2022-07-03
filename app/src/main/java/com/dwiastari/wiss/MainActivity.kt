package com.dwiastari.wiss

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dwiastari.wiss.databinding.ActivityMainBinding
import com.dwiastari.wiss.ui.admin.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide();
        setContentView(binding.root)
        //inisialisasi button login
        //pemanggilan btn login
        binding.Masuk.setOnClickListener {
            startActivity(Intent( this@MainActivity, LoginActivity::class.java))
        }
        //pemanggilan btn daftar
        binding.Daftar.setOnClickListener {
            startActivity(Intent(this@MainActivity,DaftarActivity::class.java))
        }

    }
}