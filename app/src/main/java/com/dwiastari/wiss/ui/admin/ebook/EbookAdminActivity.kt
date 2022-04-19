package com.dwiastari.wiss.ui.admin.ebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.adapter.ListEbookAdapter
import com.dwiastari.wiss.databinding.ActivityEbookAdminBinding
import com.dwiastari.wiss.model.Ebook
import com.dwiastari.wiss.ui.admin.slide.AddSlideActivity
import com.dwiastari.wiss.ui.admin.ebook.EbookAdminViewModel

class EbookAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEbookAdminBinding
    private lateinit var adapter: ListEbookAdapter
    private val viewModel: EbookAdminViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEbookAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel.apply {
            loading.observe(this@EbookAdminActivity){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
            }
            
            response.observe(this@EbookAdminActivity){
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT)
                
                if(!it.message.equals("error", ignoreCase = true))
                    finish()
            }
        }
        
        adapter = ListEbookAdapter()
        adapter.setListener(object: ListEbookAdapter.ItemListener{
            override fun onDelete(id: String) {
                viewModel.delete(id)
            }
            
            override fun onEdit(ebook: Ebook) {
                
            }
            
        })
        binding.rvEbook.setHasFixedSize(true)
        
        binding.btnAddEbook.setOnClickListener {
            startActivity(Intent(this, AddSlideActivity::class.java))
        }
        
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
    }
}