package com.dwiastari.wiss.ui.admin.slide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.R
import com.dwiastari.wiss.adapter.ListSlideAdminAdapter
import com.dwiastari.wiss.databinding.ActivityListSlideAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SlideAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListSlideAdminBinding
    private lateinit var adapter: ListSlideAdminAdapter
    private val viewModel: SlideAdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_admin)
        binding = ActivityListSlideAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ListSlideAdminAdapter()
        binding.rvSlide.setHasFixedSize(true)
        
        binding.btnAddSlide.setOnClickListener {
            startActivity(Intent(this, AddSlideActivity::class.java))
        }
        
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
    }

    private fun showRecyclerList() {
        binding.rvSlide.layoutManager = LinearLayoutManager( this)
        binding.rvSlide.adapter = adapter

        viewModel.onLoad()
        viewModel.listSlide.observe(this) {
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
