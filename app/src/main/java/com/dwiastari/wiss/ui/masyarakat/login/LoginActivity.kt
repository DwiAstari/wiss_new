package com.dwiastari.wiss.ui.masyarakat.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.dwiastari.wiss.DashboardActivity
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        loginViewModel.action.observe(this, Observer { action ->
//            when (action) {
//                LoginActivityViewModel.ACTION_LOGIN_SUCCESS -> loginSuccess()
//                LoginActivityViewModel.ACTION_LOGIN_FAILED -> loginFailed()
//                LoginActivityViewModel.ACTION_LOGIN_ERROR -> loginError()
//            }
//        })

        binding.masukbtn.setOnClickListener {
            val username = binding.tvLoginUsername.text
            val password = binding.tvLoginPassword1.text

            loginViewModel.loginAdmin(username as String?, password as String?)
        }
    }
    private fun loginError() {
        val snackbar = Snackbar.make(
            binding.root,
            "Login Gagal, Email atau Password salah",
            Snackbar.LENGTH_SHORT
        )
        val textView = snackbar.view.findViewById(R.id.snackbar_text) as TextView
        // change Snackbar text color
        textView.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.view.setBackgroundColor(Color.parseColor("#F5365C"))
        snackbar.show()
    }

    private fun loginFailed() {
        val snackbar =
            Snackbar.make(binding.root, "Login Error, server error", Snackbar.LENGTH_SHORT)
        val textView = snackbar.view.findViewById(R.id.snackbar_text) as TextView
        // change Snackbar text color
        textView.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.view.setBackgroundColor(Color.parseColor("#F0CE0E"))
        snackbar.show()
    }

    private fun loginSuccess() {
        startActivity(Intent(this, DashboardActivity::class.java))
        //SaveSharedPreference.setEmail(this, tvEmail.text.toString())
    }

}