package com.dwiastari.wiss.ui.admin.slide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ActivityListSlideAdminBinding
import com.dwiastari.wiss.ui.masyarakat.slide.MasyarakatSlideViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SlideAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListSlideAdminBinding
    private lateinit var adapter: ListSlideAdminAdapter
    private val masyarakatSlideViewModel: MasyarakatSlideViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_admin)
        binding = ActivityListSlideAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ListSlideAdminAdapter()
        binding.rvList.setHasFixedSize(true)

       showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.rvList.layoutManager = LinearLayoutManager( this)
        binding.rvList.adapter = adapter

        masyarakatSlideViewModel.onLoad()
        masyarakatSlideViewModel.listSlide.observe(this) {
            if (it != null) {
                adapter.setData(it)
            }
        }
    }
}
