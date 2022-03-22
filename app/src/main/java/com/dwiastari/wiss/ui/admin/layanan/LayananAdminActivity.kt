package com.dwiastari.wiss.ui.admin.layanan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ActivityListLayananAdminBinding
import com.dwiastari.wiss.ui.masyarakat.layanan.MasyarakatLayananViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LayananAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListLayananAdminBinding
    private lateinit var adapter: ListLayananAdminAdapter
    private val masyarakatLayananViewModel: MasyarakatLayananViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListLayananAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ListLayananAdminAdapter()
        binding.rvListLayanan.setHasFixedSize(true)

        showRecyclerList()
    }
        private fun showRecyclerList(){
            binding.rvListLayanan.layoutManager = LinearLayoutManager(this)
            binding.rvListLayanan.adapter = adapter

            masyarakatLayananViewModel.onLoad()
            masyarakatLayananViewModel.listLayanan.observe(this){
                if (it != null) {
                    adapter.setData(it)
                }
            }
        }
}