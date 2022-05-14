package com.dwiastari.wiss.ui.masyarakat.beranda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.adapter.ListEbookAdapter
import com.dwiastari.wiss.adapter.ListMasyarakatEbookAdapter
import com.dwiastari.wiss.databinding.ActivityEbookBinding
import com.dwiastari.wiss.ui.admin.ebook.EbookAdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EbookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEbookBinding
    private lateinit var adapter: ListMasyarakatEbookAdapter
    private val viewModel: EbookAdminViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEbookBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        binding.btnbackL.setOnClickListener { finish() }
        adapter = ListMasyarakatEbookAdapter()
        binding.rvEbook.setHasFixedSize(true)
        showRecyclerList()
    }
    
    private fun showRecyclerList() {
        binding.rvEbook.layoutManager = LinearLayoutManager( this)
        binding.rvEbook.adapter = adapter
        
        viewModel.onLoad()
        viewModel.listEbook.observe(this) {
            if (it != null) {
                adapter.setData(it)
            }
        }
        
        viewModel.loading.observe(this){
            binding.loading.visibility = if(it) View.VISIBLE else View.GONE
        }
    }
}