package com.dwiastari.wiss.ui.masyarakat.beranda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.adapter.ListKegiatanMasyarakatAdapter
import com.dwiastari.wiss.databinding.ActivityMasyarakatKegiatanBinding
import com.dwiastari.wiss.ui.masyarakat.artikel.MasyarakatArtikelViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MasyarakatKegiatanActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMasyarakatKegiatanBinding
    private lateinit var adapter: ListKegiatanMasyarakatAdapter
    private val masyarakatArtikelViewModel: MasyarakatArtikelViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMasyarakatKegiatanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        adapter = ListKegiatanMasyarakatAdapter()
        binding.rvKegiatan.setHasFixedSize(true)
    
        binding.btnbackL.setOnClickListener { finish() }
        
        showRecyclerList()
    }
    
    private fun showRecyclerList() {
        binding.rvKegiatan.layoutManager = LinearLayoutManager(this)
        binding.rvKegiatan.adapter = adapter
        
        binding.loading.visibility = View.VISIBLE
        masyarakatArtikelViewModel.onLoad()
        masyarakatArtikelViewModel.listArticle.observe(this) {
            if (it != null) {
                adapter.setData(it)
                binding.loading.visibility = View.GONE
            }
        }
    }
}