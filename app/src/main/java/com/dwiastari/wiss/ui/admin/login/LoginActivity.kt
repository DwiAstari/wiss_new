package com.dwiastari.wiss.ui.admin.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dwiastari.wiss.ui.admin.DashboardActivity
import com.dwiastari.wiss.api.RetrofitClient
import com.dwiastari.wiss.databinding.ActivityLoginBinding
import com.dwiastari.wiss.model.ResponseLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(){
    private var binding : ActivityLoginBinding? = null
    private var user : String = ""
    private var pass : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnLogin.setOnClickListener {
            user = binding!!.etUsername.text.toString()
            pass = binding!!.etPassword.text.toString()

            when {
                user == "" -> {
                    binding!!.etUsername.error = " Username tidak boleh kosong"
                }
                pass == "" -> {
                    binding!!.etPassword.error = " password tidak boleh kosong"
                }
                else -> {
                    binding!!.loading.visibility = View.VISIBLE
                getData()
                }
            }

        }
    }

    private fun getData () {
        val api = RetrofitClient().getInstance()
        api.loginAdmin(user,pass).enqueue(object : Callback<ResponseLogin>{

        override fun onResponse(call: Call<ResponseLogin>, response : Response<ResponseLogin>){
            if (response.body()?.response == true){
                binding!!.loading.visibility = View.GONE
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                finish()
            }else{
                binding!!.loading.visibility = View.GONE
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

}