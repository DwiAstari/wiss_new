package com.dwiastari.wiss.ui.admin.video

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ActivityListVideoAdminBinding
import com.dwiastari.wiss.ui.admin.slide.ListSlideAdminAdapter
import com.dwiastari.wiss.ui.masyarakat.video.MasyarakatVideoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListVideoAdminBinding
    private lateinit var adapter: ListSlideAdminAdapter
    private val masyarakatVideoViewModel: MasyarakatVideoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_admin)
        binding = ActivityListVideoAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ListVideoAdminAdapter()
        binding.rvListVideo.setHasFixedSize(true)

        showRecycleList()
    }

   private fun showRecycleList() {
       binding.rvListVideo.layoutManager = LinearLayoutManager(this)
       binding.rvListVideo.adapter = adapter

       masyarakatVideoViewModel.onLoad()
       masyarakatVideoViewModel.listVideo.observe(this){
           if (it != null) {
               adapter.setData(it)
           }
       }
   }
}