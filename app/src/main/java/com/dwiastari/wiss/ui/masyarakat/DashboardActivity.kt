package com.dwiastari.wiss.ui.masyarakat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ActivityDashboardBinding
import com.dwiastari.wiss.ui.masyarakat.about.AboutFragment
import com.dwiastari.wiss.ui.masyarakat.beranda.BerandaFragment
import com.dwiastari.wiss.ui.masyarakat.pesan.PesanFragment
import com.dwiastari.wiss.ui.masyarakat.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
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
    }
    
    private fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}