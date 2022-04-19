package com.dwiastari.wiss.ui.admin.ebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dwiastari.wiss.databinding.ActivityAddEbookBinding
import com.dwiastari.wiss.model.Ebook
import com.dwiastari.wiss.ui.admin.ebook.AddEbookViewModel
import com.dwiastari.wiss.utils.EmptyTextWatcher

class AddEbookActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_EBOOK = "ebook"
    }
    
    private lateinit var binding: ActivityAddEbookBinding
    private val viewModel: AddEbookViewModel by viewModels()
    private var isEdit = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEbookBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        val ebook = intent.extras?.getParcelable<Ebook>(EXTRA_EBOOK)
        isEdit = ebook == null
    
        binding.apply {
            edtJudul.addTextChangedListener(EmptyTextWatcher(layoutJudul, "Isi Judul"))
            edtLink.addTextChangedListener(EmptyTextWatcher(layoutLink, "Isi Link"))
        
            if (isEdit) {
                tvJudul.text = "Edit Ebook"
                edtJudul.setText(ebook?.judul_ebook)
                edtLink.setText(ebook?.link_ebook)
            }
        
            btnadd.setOnClickListener {
                val judul = edtJudul.text.toString().trim()
                val link = edtLink.text.toString().trim()
            
                var error = false
            
                if (judul.isEmpty()) {
                    error = true
                    layoutJudul.error = "Isi Judul"
                }
            
                if (link.isEmpty()) {
                    error = true
                    layoutLink.error = "Isi Link"
                }
            
                if (error) {
                    Toast.makeText(this@AddEbookActivity, "Periksa kembali inputan anda", Toast.LENGTH_SHORT).show()
                } else {
                    if (isEdit)
                        viewModel.updateEbook(ebook!!.id_ebook, judul, link)
                    else
                        viewModel.createEbook(judul, link)
                }
            }
        }
    
        viewModel.apply {
            loading.observe(this@AddEbookActivity) {
                binding.loading.visibility = if (it) View.VISIBLE else View.GONE
            }
        
            response.observe(this@AddEbookActivity) {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                if (it.message != "Error")
                    finish()
            }
        }
    }
}