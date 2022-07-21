package com.dwiastari.wiss.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dwiastari.wiss.MainActivity
import com.dwiastari.wiss.api.RetrofitClient
import com.dwiastari.wiss.databinding.ActivityDashboardAdminBinding
import com.dwiastari.wiss.model.UserCountResponse
import com.dwiastari.wiss.ui.admin.ebook.EbookAdminActivity
import com.dwiastari.wiss.ui.admin.kegiatan.KegiatanAdminActivity
import com.dwiastari.wiss.ui.admin.konselor.ListKonselorActivity
import com.dwiastari.wiss.ui.admin.layanan.LayananAdminActivity
import com.dwiastari.wiss.ui.admin.slide.SlideAdminActivity
import com.dwiastari.wiss.ui.admin.video.VideoAdminActivity
import com.dwiastari.wiss.ui.masyarakat.profile.EditPasswordActivity
import com.dwiastari.wiss.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardAdminBinding
    private val TIME_INTERVAL = 2000 // # milliseconds, desired time passed between two back presses.
    private var mBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            cardKegiatan.setOnClickListener {
                startActivity(Intent(this@DashboardAdminActivity, KegiatanAdminActivity::class.java))
            }
            cardLayanan.setOnClickListener{
                startActivity(Intent(this@DashboardAdminActivity, LayananAdminActivity::class.java))
            }
            cardSlide.setOnClickListener {
                startActivity(Intent(this@DashboardAdminActivity, SlideAdminActivity::class.java))
            }
            cardVideo.setOnClickListener {
                startActivity(Intent(this@DashboardAdminActivity, VideoAdminActivity::class.java))
            }
            cardBook.setOnClickListener {
                startActivity(Intent(this@DashboardAdminActivity, EbookAdminActivity::class.java))
            }
            cardKonselor.setOnClickListener {
                startActivity(Intent(this@DashboardAdminActivity, ListKonselorActivity::class.java))
            }
            cardChangePassword.setOnClickListener {
                startActivity(Intent(this@DashboardAdminActivity, EditPasswordActivity::class.java))
            }
            btnLogout.setOnClickListener {
                logout()
            }
    
            val preferences = getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)
            val userType = preferences.getString(Constant.KEY_TYPE, "")
            
            if(userType?.lowercase() == "superadmin"){
                cardChangePassword.visibility = View.GONE
            } else {
                layoutGrid.columnCount = 1
                cardLayanan.visibility = View.GONE
                cardSlide.visibility = View.GONE
                cardVideo.visibility = View.GONE
                cardBook.visibility = View.GONE
                cardKonselor.visibility = View.GONE
                cardChangePassword.visibility = View.VISIBLE
            }
        }
    
        val api = RetrofitClient().getInstance()
        api.getUserCount().enqueue(object: Callback<UserCountResponse> {
            override fun onResponse(call: Call<UserCountResponse>, response: Response<UserCountResponse>) {
                if(response.isSuccessful){
                    binding.jmlKunjungan.setText(response.body()?.total.toString())
                }
            }
    
            override fun onFailure(call: Call<UserCountResponse>, t: Throwable) {
            
            }
    
        })
        
        val firebase = FirebaseDatabase.getInstance()
        val db = firebase.reference
        
        db.child("chat").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val number = snapshot.childrenCount / 2
                binding.textView18.text = "Jumlah Konseling: $number"
            }
    
            override fun onCancelled(p0: DatabaseError) {
            
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
    
    private fun logout(){
        val preferences = getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
        
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}