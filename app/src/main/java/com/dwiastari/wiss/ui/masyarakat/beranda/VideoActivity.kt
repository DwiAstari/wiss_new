package com.dwiastari.wiss.ui.masyarakat.beranda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.adapter.ListMasyarakatEbookAdapter
import com.dwiastari.wiss.adapter.ListMasyarakatVideoAdapter
import com.dwiastari.wiss.databinding.ActivityEbookBinding
import com.dwiastari.wiss.databinding.ActivityVideoBinding
import com.dwiastari.wiss.ui.admin.video.VideoAdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding
    private lateinit var adapter: ListMasyarakatVideoAdapter
    private val viewModel: VideoAdminViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        binding.btnbackL.setOnClickListener { finish() }
        adapter = ListMasyarakatVideoAdapter()
        binding.rvVideo.setHasFixedSize(true)
        binding.rvVideo.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        showRecyclerList()
    }
    
    private fun showRecyclerList() {
        binding.rvVideo.layoutManager = LinearLayoutManager( this)
        binding.rvVideo.adapter = adapter
        
        viewModel.onLoad()
        viewModel.listVideo.observe(this) {
            if (it != null) {
                adapter.setData(it)
            }
        }
        
        viewModel.loading.observe(this){
            binding.loading.visibility = if(it) View.VISIBLE else View.GONE
        }
    }
}