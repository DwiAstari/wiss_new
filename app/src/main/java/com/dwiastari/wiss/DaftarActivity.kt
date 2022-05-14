package com.dwiastari.wiss

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.dwiastari.wiss.api.RetrofitClient
import com.dwiastari.wiss.databinding.ActivityDaftarBinding
import com.dwiastari.wiss.model.DefaultResponse
import com.dwiastari.wiss.model.RegisterResponse
import com.dwiastari.wiss.ui.masyarakat.artikel.MasyarakatArtikelViewModel
import com.dwiastari.wiss.utils.EmptyTextWatcher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_daftar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

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
            
            btnbackD.setOnClickListener{
                finish()
            }
            
            binding.btnRegister.setOnClickListener{
                val nama = etNama.text.toString().trim()
                val username = etUsername.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val age = etAge.text.toString().trim()
                val address = etAddress.text.toString().trim()
                
                var error = false;
                
                error = nama.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || age.isEmpty() || address.isEmpty()
                        || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                
                layoutEmail.error = if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Email tidak sesuai formatt" else null
                
                if(error){
                    Toast.makeText(this@DaftarActivity, "Periksa kembali inputan anda", Toast.LENGTH_SHORT).show()
                } else {
                    register(nama, username, email, password, phone, age, address)
                }
            }
        }
        
        
    }
    
    private fun register(nama: String, username: String, email: String, password: String, phone: String, age: String, address: String){
        val api = RetrofitClient().getInstance()
        binding.loading.visibility = View.VISIBLE
        
        api.registerUser(username, password, nama, email, phone, age, address).enqueue(object: Callback<DefaultResponse>{
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                binding.loading.visibility = View.GONE
                if(response.isSuccessful){
                    Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    if(response.body()?.message!!.contains("Berhasil")){
                        finish()
                    }
                } else {
                    Toast.makeText(this@DaftarActivity, "Gagal Daftar, Silahkan coba kembali", Toast.LENGTH_SHORT).show()
                }
            }
    
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                binding.loading.visibility = View.GONE
                Toast.makeText(this@DaftarActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    
        })
    }
}