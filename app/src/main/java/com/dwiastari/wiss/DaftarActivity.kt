package com.dwiastari.wiss

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dwiastari.wiss.databinding.ActivityDaftarBinding
import com.dwiastari.wiss.ui.masyarakat.artikel.MasyarakatArtikelViewModel
import com.dwiastari.wiss.utils.EmptyTextWatcher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_daftar.*

@AndroidEntryPoint
class DaftarActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityDaftarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        with(binding){
            etNama.addTextChangedListener(EmptyTextWatcher(layoutNama, "Nama Tidak Boleh Kosong"))
            etUsername.addTextChangedListener(EmptyTextWatcher(layoutUsername, "Username Tidak Boleh Kosong"))
            etEmail.addTextChangedListener(EmptyTextWatcher(layoutEmail, "Email Tidak Boleh Kosong"))
            etPassword.addTextChangedListener(EmptyTextWatcher(layoutPassword, "Password Tidak Boleh Kosong"))
            etPhone.addTextChangedListener(EmptyTextWatcher(layoutPhone, "No. HP Tidak Boleh Kosong"))
            etAge.addTextChangedListener(EmptyTextWatcher(layoutAge, "Umur Tidak Boleh Kosong"))
            etAddress.addTextChangedListener(EmptyTextWatcher(layoutAddress, "Domisili Tidak Boleh Kosong"))
            etRole.addTextChangedListener(EmptyTextWatcher(layoutRole, "Role Tidak Boleh Kosong"))
            
            btnbackD.setOnClickListener{
                finish()
            }
            
            val listRole = arrayOf("masyarakat", "konselor")
            val arrayAdapter = ArrayAdapter<String>(this@DaftarActivity, R.layout.item_dropdown_role, listRole)
            etRole.setAdapter(arrayAdapter)
            
            binding.btnRegister.setOnClickListener{
            
            }
        }
        
        
    }
}