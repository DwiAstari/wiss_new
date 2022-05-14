package com.dwiastari.wiss.ui.masyarakat.beranda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.adapter.LayananMasyarakatAdapter
import com.dwiastari.wiss.adapter.ListLayananAdminAdapter
import com.dwiastari.wiss.databinding.ActivityInformasiBinding
import com.dwiastari.wiss.ui.admin.layanan.LayananAdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformasiActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityInformasiBinding
    private lateinit var adapter: LayananMasyarakatAdapter
    private val masyarakatLayananViewModel: LayananAdminViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnbackL.setOnClickListener { finish() }
        adapter = LayananMasyarakatAdapter()
        binding.rvLayanan.setHasFixedSize(true)
        showRecyclerList()
        
    }
    
    private fun showRecyclerList(){
        binding.rvLayanan.layoutManager = LinearLayoutManager(this)
        binding.rvLayanan.adapter = adapter
        
        masyarakatLayananViewModel.onLoad()
        masyarakatLayananViewModel.listLayanan.observe(this){
            if (it != null) {
                adapter.setData(it)
            }
        }
        
        
    }
}