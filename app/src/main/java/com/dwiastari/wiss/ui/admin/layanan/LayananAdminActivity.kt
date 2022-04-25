package com.dwiastari.wiss.ui.admin.layanan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.adapter.ListLayananAdminAdapter
import com.dwiastari.wiss.databinding.ActivityListLayananAdminBinding
import com.dwiastari.wiss.ui.masyarakat.layanan.MasyarakatLayananViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LayananAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListLayananAdminBinding
    private lateinit var adapter: ListLayananAdminAdapter
    private val masyarakatLayananViewModel: LayananAdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListLayananAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ListLayananAdminAdapter()
        binding.rvLayanan.setHasFixedSize(true)
        binding.btnbackL.setOnClickListener { finish() }

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
    
    override fun onResume() {
        super.onResume()
        showRecyclerList()
    }
}