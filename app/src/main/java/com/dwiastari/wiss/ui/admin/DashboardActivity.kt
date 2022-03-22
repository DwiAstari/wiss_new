package com.dwiastari.wiss.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.dwiastari.wiss.R
import com.dwiastari.wiss.ui.admin.layanan.LayananAdminActivity
import com.dwiastari.wiss.ui.admin.slide.SlideAdminActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        card_kegiatan.setOnClickListener {
            startActivity(Intent(this,KegiatanAdminActivity::class.java))
        }
        card_layanan.setOnClickListener{
            startActivity(Intent(this,LayananAdminActivity::class.java))
        }
        card_slide.setOnClickListener {
            startActivity(Intent(this, SlideAdminActivity::class.java))
        }


    }
}