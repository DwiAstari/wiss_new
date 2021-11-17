package com.dwiastari.wiss

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.dwiastari.wiss.adapter.OnBoardingViewPagerAdapter
import com.dwiastari.wiss.model.OnBoardingData
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager : ViewPager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //insialisasi button daftar
        val mStartActBtn = findViewById<Button>(R.id.Daftar)
        //pemanggilan btn daftar
        mStartActBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity,DaftarActivity::class.java))
        }
        //inisialisasi button login
        val mStartActBtn1 = findViewById<Button>(R.id.Masuk)
        //pemanggilan btn login
        mStartActBtn1.setOnClickListener {
            startActivity(Intent( this@MainActivity,LoginActivity::class.java))
        }

        //inisialisasi tablayout untuk memanggil tab indicator pada activity main
        tabLayout = findViewById(R.id.tab_indicator)

        //menambahkan list data yang akan ditampilkan
        val onBoardingData:MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData("Keluargaku","Pusat Pelayanan Keluarga Sejahtera Lancang Kuning", R.drawable.logokeluargaku))
        onBoardingData.add(OnBoardingData("Konseling Pranikah","Layanan Konseling Pranikah",R.drawable.nikah))
        onBoardingData.add(OnBoardingData("Konseling Anak","Layanan Konseling Keluarga Balita dan Anak",R.drawable.anak))
        onBoardingData.add(OnBoardingData("Konseling Lansia","Layanan Konseling Keluarga Lansia dan Lansia",R.drawable.lansia))
        onBoardingData.add(OnBoardingData("Konseling KB","Layanan Konseling KB dan Kes.Reproduksi",R.drawable.programkb))
        onBoardingData.add(OnBoardingData("Konseling Remaja","Layanan Konseling Keluarga Remaja dan Remaja",R.drawable.remaja))
        onBoardingData.add(OnBoardingData("Ekonomi Keluarga","Layanan Pembinaan Usaha Ekonomi Keluarga",R.drawable.ekonomi))
        onBoardingData.add(OnBoardingData("Informasi Kependudukan","Layanan Informasi Kependudukan dan KB",R.drawable.kb))

        //menampilkan semua data secara berurut
        setOnBoardingViewPagerAdapter(onBoardingData)

    }
    //set list data agar dapat terbaca pada adapternya
    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>){
        onBoardingViewPager = findViewById(R.id.screenPager);
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
        tabLayout?.setupWithViewPager(onBoardingViewPager)

    }
}