package com.dwiastari.wiss.ui.admin.video

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dwiastari.wiss.databinding.ActivityAddVideoBinding
import com.dwiastari.wiss.model.Video
import com.dwiastari.wiss.utils.EmptyTextWatcher
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddVideoActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_VIDEO = "video"
    }
    
    private lateinit var binding: ActivityAddVideoBinding
    private val viewModel: AddVideoViewModel by viewModels()
    private var isEdit = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val video = intent.extras?.getParcelable<Video>(EXTRA_VIDEO)
        isEdit = video == null
    
        binding.apply {
            edtJudul.addTextChangedListener(EmptyTextWatcher(layoutJudul, "Isi Judul"))
            edtLink.addTextChangedListener(EmptyTextWatcher(layoutLink, "Isi Link"))
            
            if(isEdit){
                tvJudul.text = "Edit Video"
                edtJudul.setText(video?.judul_video)
                edtLink.setText(video?.link_video)
            }
    
            btnadd.setOnClickListener {
                val judul = edtJudul.text.toString().trim()
                val link = edtLink.text.toString().trim()
        
                var error = false
        
                if(judul.isEmpty()){
                    error = true
                    layoutJudul.error = "Isi Judul"
                }
        
                if(link.isEmpty()){
                    error = true
                    layoutLink.error = "Isi Link"
                }
        
                if(error){
                    Toast.makeText(this@AddVideoActivity, "Periksa kembali inputan anda", Toast.LENGTH_SHORT).show()
                } else {
                    if(isEdit)
                        viewModel.updateVideo(video!!.id_video, judul, link)
                    else
                        viewModel.createVideo(judul, link)
                }
            }
        }
    
        viewModel.apply {
            loading.observe(this@AddVideoActivity){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
            }
        
            response.observe(this@AddVideoActivity){
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                if(it.message != "Error")
                    finish()
            }
        }
    }
}