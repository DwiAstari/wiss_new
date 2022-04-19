package com.dwiastari.wiss.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dwiastari.wiss.api.RetrofitClient
import com.dwiastari.wiss.databinding.ActivityDashboardAdminBinding
import com.dwiastari.wiss.model.UserCountResponse
import com.dwiastari.wiss.ui.admin.ebook.EbookAdminActivity
import com.dwiastari.wiss.ui.admin.kegiatan.KegiatanAdminActivity
import com.dwiastari.wiss.ui.admin.layanan.LayananAdminActivity
import com.dwiastari.wiss.ui.admin.slide.SlideAdminActivity
import com.dwiastari.wiss.ui.admin.video.VideoAdminActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            cardVideo.setOnClickListener {
                startActivity(Intent(this@DashboardAdminActivity, VideoAdminActivity::class.java))
            }
            cardBook.setOnClickListener {
                startActivity(Intent(this@DashboardAdminActivity, EbookAdminActivity::class.java))
            }
            
            cardKeluar.setOnClickListener {
                finish()
            }
        }
    
        val api = RetrofitClient().getInstance()
        api.getUserCount().enqueue(object: Callback<UserCountResponse> {
            override fun onResponse(call: Call<UserCountResponse>, response: Response<UserCountResponse>) {
                if(response.isSuccessful){
                    binding.jmlKunjungan.setText(response.body()?.total)
                }
            }
    
            override fun onFailure(call: Call<UserCountResponse>, t: Throwable) {
            
            }
    
        })
    }
}