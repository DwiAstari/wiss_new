package com.dwiastari.wiss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.dwiastari.wiss.adapter.OnBoardingViewPagerAdapter
import com.dwiastari.wiss.model.OnBoardingData
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_walk_trough.*

class WalkTroughActivity : AppCompatActivity() {

    var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager : ViewPager? = null

    val dots = arrayOfNulls<TextView>(7)
    var currentpage: Int = 0
    val  a: Int = 8
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walk_trough)

        dotIndicator(currentpage)

        initAction()

        //menambahkan list data yang akan ditampilkan
        val onBoardingData:MutableList<OnBoardingData> = ArrayList()
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

    fun initAction() {
        screenPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                dotIndicator(position)
                currentpage = position
            }

        })

        tv_lanjutkan.setOnClickListener {
            if (screenPager.currentItem + 1 < dots.size) {
                screenPager.currentItem += 1
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        tv_lewati.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun dotIndicator(p: Int){
        ll_dots.removeAllViews()

        for (i in 0..dots.size-1){
            dots[i] = TextView(this)
            dots[i]?.textSize = 35f
            dots[i]?.setTextColor(resources.getColor(R.color.textColorSecondary))
        }

        if (dots.size > 0){
            dots[p]?.setTextColor(resources.getColor(R.color.primaryVariant))
        }
    }
}