package com.dwiastari.wiss.ui.admin.layanan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dwiastari.wiss.databinding.ActivityEditLayananBinding
import com.dwiastari.wiss.model.Layanan
import com.dwiastari.wiss.utils.EmptyTextWatcher
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditLayananActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_LAYANAN = "layanan"
    }
    
    private lateinit var binding: ActivityEditLayananBinding
    private val viewModel: EditLayananViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditLayananBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val layanan = intent.extras?.getParcelable<Layanan>(EXTRA_LAYANAN)
        
        binding.apply {
            edtIsiLayanan.addTextChangedListener(EmptyTextWatcher(layoutIsiLayanan, "Isi layanan tidak boleh kosong"))
            
            edtHari.setText(layanan?.hari)
            edtIsiLayanan.setText(layanan?.layanan)
            
            btnbackL.setOnClickListener { finish() }
            
            btnSave.setOnClickListener {
                val isiLayanan = edtIsiLayanan.text.toString()
                
                if(isiLayanan.isEmpty()){
                    edtIsiLayanan.error = "Isi layanan tidak boleh kosong"
                } else {
                    viewModel.updateLayanan(layanan!!.id_hari, isiLayanan)
                }
            }
        }
        
        viewModel.apply {
            loading.observe(this@EditLayananActivity){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
            }
            
            response.observe(this@EditLayananActivity){
                Toast.makeText(this@EditLayananActivity, it.message, Toast.LENGTH_SHORT).show()
                if(it.message != "Error"){
                    finish()
                }
            }
        }
    }
}