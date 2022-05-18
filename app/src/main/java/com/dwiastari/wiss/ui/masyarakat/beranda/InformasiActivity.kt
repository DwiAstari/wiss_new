package com.dwiastari.wiss.ui.masyarakat.beranda

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dwiastari.wiss.R
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
        
//        setContentView(R.layout.activity_informasi)
//
//        val btnBack = findViewById<ImageButton>(R.id.btnback_l)
        
        binding.btnbackL.setOnClickListener {
            finish()
        }
        
        binding.tvBkkbn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.bkkbn.go.id/#")
            startActivity(intent)
        }
        
        binding.tvBkkbn.setOnClickListener {  }
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