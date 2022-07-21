package com.dwiastari.wiss.ui.admin.kegiatan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.databinding.ActivityListKegiatanAdminBinding
import com.dwiastari.wiss.adapter.ListKegiatanAdminAdapter
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
        
        binding.btnbackL.setOnClickListener { finish() }
    
        binding.etCari.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            
            }
        
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            
            }
        
            override fun afterTextChanged(s: Editable?) {
                adapter.filterData(s.toString())
            }
        
        })
        
        binding.btnAddArticle.setOnClickListener { startActivity(Intent(this, IsiKegiatanAdminActivity::class.java)) }
    }

    private fun showRecyclerList() {
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter

        binding.loading.visibility = View.VISIBLE
        
        masyarakatArtikelViewModel.onLoad()
        masyarakatArtikelViewModel.listArticle.observe(this) {
            if (it != null) {
                adapter.setData(it)
                binding.loading.visibility = View.GONE
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        showRecyclerList()
    }
}