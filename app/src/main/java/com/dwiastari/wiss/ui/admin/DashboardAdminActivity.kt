package com.dwiastari.wiss.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ActivityDashboardAdminBinding
import com.dwiastari.wiss.ui.admin.layanan.LayananAdminActivity
import com.dwiastari.wiss.ui.admin.slide.SlideAdminActivity

class DashboardAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            cardKegiatan.setOnClickListener {
                startActivity(Intent(this@DashboardAdminActivity, KegiatanAdminActivity::class.java))
            }
            cardLayanan.setOnClickListener{
                startActivity(Intent(this@DashboardAdminActivity, LayananAdminActivity::class.java))
            }
            cardSlide.setOnClickListener {
                startActivity(Intent(this@DashboardAdminActivity, SlideAdminActivity::class.java))
            }
        }


    }
}