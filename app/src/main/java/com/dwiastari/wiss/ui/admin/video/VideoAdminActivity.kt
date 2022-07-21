package com.dwiastari.wiss.ui.admin.video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiastari.wiss.adapter.ListSlideAdminAdapter
import com.dwiastari.wiss.adapter.ListVideoAdapter
import com.dwiastari.wiss.databinding.ActivityVideoAdminBinding
import com.dwiastari.wiss.model.Video
import com.dwiastari.wiss.ui.admin.slide.AddSlideActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoAdminBinding
    private lateinit var adapter: ListVideoAdapter
    private val viewModel: VideoAdminViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        viewModel.apply {
            loading.observe(this@VideoAdminActivity){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
            }
            
            response.observe(this@VideoAdminActivity){
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT)
                
                if(!it.message.equals("error", ignoreCase = true))
                    finish()
            }
        }
    
        adapter = ListVideoAdapter()
        adapter.setListener(object: ListVideoAdapter.ItemListener{
            override fun onDelete(id: String) {
                viewModel.delete(id)
            }
    
            override fun onEdit(video: Video) {
                val intent = Intent(this@VideoAdminActivity, AddVideoActivity::class.java)
                intent.putExtra(AddVideoActivity.EXTRA_VIDEO, video)
                startActivity(intent)
            }
    
        })
        binding.rvVideo.setHasFixedSize(true)
    
        binding.btnAddVideo.setOnClickListener {
            startActivity(Intent(this, AddVideoActivity::class.java))
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
        binding.rvVideo.layoutManager = LinearLayoutManager( this)
        binding.rvVideo.adapter = adapter
        
        viewModel.onLoad()
        viewModel.listVideo.observe(this) {
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