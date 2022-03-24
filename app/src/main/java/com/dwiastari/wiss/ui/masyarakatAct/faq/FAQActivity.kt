package com.dwiastari.wiss.ui.masyarakatAct.faq

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dwiastari.wiss.MainActivity
import com.dwiastari.wiss.R
import kotlinx.android.synthetic.main.activity_faqactivity.*

class FAQActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faqactivity)

        btnback_f.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }}