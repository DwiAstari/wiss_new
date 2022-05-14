package com.dwiastari.wiss.ui.masyarakat.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dwiastari.wiss.databinding.ActivityEditPasswordBinding
import com.dwiastari.wiss.utils.Constant
import com.dwiastari.wiss.utils.EmptyTextWatcher
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditPasswordBinding
    private val viewModel: EditPasswodViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val preferences = getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)
        val username = preferences.getString(Constant.KEY_USERNAME, "")
        val type = preferences.getString(Constant.KEY_TYPE, "")
        
        with(binding){
            etOldPassword.addTextChangedListener(EmptyTextWatcher(layoutOldPassword, "Password lama tidak boleh kosong"))
            etNewPassword.addTextChangedListener(EmptyTextWatcher(layoutNewPassword, "Password baru tidak boleh kosong"))
            
            btnbackL.setOnClickListener { finish() }
            btnSave.setOnClickListener {
                val oldPassword = etOldPassword.text.toString()
                val newPassword = etNewPassword.text.toString()
                
                var error = false
                
                if(oldPassword.isEmpty()){
                    error = true
                    layoutOldPassword.error = "Password lama tidak boleh kosong"
                }
    
                if(newPassword.isEmpty()){
                    error = true
                    layoutNewPassword.error = "Password baru tidak boleh kosong"
                }
                
                if(error){
                    Toast.makeText(this@EditPasswordActivity, "Mohon periksa kembali inputan anda", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.changePassword(type!!, username!!, oldPassword, newPassword)
                }
            }
        }
        
        with(viewModel){
            isLoading.observe(this@EditPasswordActivity){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
            }
    
            message.observe(this@EditPasswordActivity){
                Toast.makeText(this@EditPasswordActivity, it, Toast.LENGTH_SHORT).show()
                if(it.contains("updated")){
                    finish()
                }
            }
        }
        
    }
}