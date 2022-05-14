package com.dwiastari.wiss.ui.admin.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dwiastari.wiss.DaftarActivity
import com.dwiastari.wiss.MainActivity
import com.dwiastari.wiss.api.RetrofitClient
import com.dwiastari.wiss.databinding.ActivityLoginBinding
import com.dwiastari.wiss.model.ResponseLogin
import com.dwiastari.wiss.ui.admin.DashboardAdminActivity
import com.dwiastari.wiss.ui.masyarakat.DashboardActivity
import com.dwiastari.wiss.utils.Constant
import com.dwiastari.wiss.utils.EmptyTextWatcher
import kotlinx.android.synthetic.main.activity_daftar.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding
    private var user : String = ""
    private var pass : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnback_l.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        
        with(binding){
            etUsername.addTextChangedListener(EmptyTextWatcher(layoutUsername, "Username tidak boleh kosong"))
            etPassword.addTextChangedListener(EmptyTextWatcher(layoutPassword, "Password tidak boleh kosong"))
    
            btnLogin.setOnClickListener {
                user = etUsername.text.toString()
                pass = etPassword.text.toString()
        
                when {
                    user == "" -> {
                        layoutUsername.error = " Username tidak boleh kosong"
                    }
                    pass == "" -> {
                        layoutPassword.error = " password tidak boleh kosong"
                    }
                    else -> {
                        loading.visibility = View.VISIBLE
                        getData()
                    }
                }
            }
            
            btnDaftar.setOnClickListener {
                startActivity(Intent(this@LoginActivity, DaftarActivity::class.java))
            }
        }
    }

    private fun getData () {
        val api = RetrofitClient().getInstance()
        api.loginUser(user,pass).enqueue(object : Callback<ResponseLogin>{

        override fun onResponse(call: Call<ResponseLogin>, response : Response<ResponseLogin>){
            if (response.body()?.response == true){
                binding.loading.visibility = View.GONE
                if(response.body()?.payload?.type.equals("admin")) {
                    response.body()?.payload?.type?.let { saveData(user, it) }
                    startActivity(Intent(this@LoginActivity, DashboardAdminActivity::class.java))
                }else {
                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                    response.body()?.payload?.type?.let { saveData(user, it) }
                }
                finish()
            }else{
                binding.loading.visibility = View.GONE
                Toast.makeText(
                    this@LoginActivity,
                    "login gagal, periksa lagi username dan password",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e("pesan error", "${t.message}")
            }

        })

    }
    
    
    
    private fun saveData (username: String, type: String){
        val preferences = getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)
        val editor = preferences.edit()
        
        editor.putString(Constant.KEY_USERNAME, username)
        editor.putString(Constant.KEY_TYPE, type)
        editor.apply()
    }

}