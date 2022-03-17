package com.dwiastari.wiss.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ActivityIsiKegiatanAdminBinding
import com.dwiastari.wiss.databinding.ActivityListKegiatanAdminBinding
import com.dwiastari.wiss.ui.masyarakat.artikel.MasyarakatArtikelViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KegiatanAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListKegiatanAdminBinding
    private lateinit var adapter: ListKegiatanAdminAdapter
    private val masyarakatArtikelViewModel: MasyarakatArtikelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListKegiatanAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ListKegiatanAdminAdapter()
        binding.rvList.setHasFixedSize(true)

        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter

        masyarakatArtikelViewModel.onLoad()
        masyarakatArtikelViewModel.listArticle.observe(this) {
            if (it != null) {
                adapter.setData(it)
            }
        }
    }
}