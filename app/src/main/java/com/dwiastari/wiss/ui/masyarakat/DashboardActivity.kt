package com.dwiastari.wiss.ui.masyarakat

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dwiastari.wiss.R
import com.dwiastari.wiss.api.RetrofitClient
import com.dwiastari.wiss.databinding.ActivityDashboardBinding
import com.dwiastari.wiss.model.DefaultResponse
import com.dwiastari.wiss.ui.masyarakat.about.AboutFragment
import com.dwiastari.wiss.ui.masyarakat.beranda.BerandaFragment
import com.dwiastari.wiss.ui.masyarakat.pesan.PesanFragment
import com.dwiastari.wiss.ui.masyarakat.profile.ProfileFragment
import com.dwiastari.wiss.utils.Constant
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private val TIME_INTERVAL = 2000 // # milliseconds, desired time passed between two back presses.
    private var mBackPressed: Long = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(
                object : OnCompleteListener<String?> {
                    override fun onComplete(task: Task<String?>) {
                        if (!task.isSuccessful) {
                            Log.w("FirebaseToken", "Fetching FCM registration token failed", task.exception)
                            return
                        }
                    
                        // Get new FCM registration token
                        val token = task.result
                    
                        // Log and toast
                        Log.d("FirebaseToken", token!!)
                        val sharedPreferences: SharedPreferences = getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)
                        val username = sharedPreferences.getString(Constant.KEY_USERNAME, "")
                        val editor = sharedPreferences.edit()
                        editor.putString("fcmtoken", token)
                        editor.apply()
                        
                        registerToken(username!!, token)
                    }
                })
        
        binding.bnv.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.item_beranda -> {
                    loadFragment(BerandaFragment())
                    true
                }
                R.id.item_pesan -> {
                    loadFragment(PesanFragment())
                    true
                }
                R.id.item_about -> {
                    loadFragment(AboutFragment())
                    true
                }
                R.id.item_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
        
        binding.bnv.selectedItemId = R.id.item_beranda
    }
    
    private fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
    
    private fun registerToken(username: String, token: String){
        val api = RetrofitClient().getInstance()
        api.addToken(username, token).enqueue(object: Callback<DefaultResponse>{
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                Log.d("response", response.body()!!.message)
            }
    
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.d("response", t.localizedMessage)
            }
    
        })
    }
    
    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity()
        } else {
            Toast.makeText(baseContext, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
        }
        mBackPressed = System.currentTimeMillis()
        
    }
    
    
}