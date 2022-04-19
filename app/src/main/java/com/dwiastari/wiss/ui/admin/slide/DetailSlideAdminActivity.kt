package com.dwiastari.wiss.ui.admin.slide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dwiastari.wiss.databinding.ActivityDetailSlideAdminBinding
import com.dwiastari.wiss.model.Slide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailSlideAdminActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_SLIDE = "slide"
    }
    
    private lateinit var binding: ActivityDetailSlideAdminBinding
    private val viewModel: DetailSlideViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSlideAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val slide = intent.extras?.getParcelable<Slide>(EXTRA_SLIDE)
        
        viewModel.apply {
            loading.observe(this@DetailSlideAdminActivity){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
            }
            
            response.observe(this@DetailSlideAdminActivity){
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                
                if(it.message != "Error"){
                    finish()
                }
            }
        }
        
        binding.apply {
            btnbackL.setOnClickListener { finish() }
            judulSlide.text = slide?.judul_slide
            statusSlide.text = "Status: ${slide?.status}"
            
            Glide.with(this@DetailSlideAdminActivity)
                .load(slide?.foto_slides)
                .into(imgSlides)
            
            btnDelete.setOnClickListener {
            
            }
            
            btnEdit.setOnClickListener {
                val intent = Intent(this@DetailSlideAdminActivity, AddSlideActivity::class.java)
                intent.putExtra(EXTRA_SLIDE, slide)
                startActivity(intent)
            }
        }
    }
}