package com.dwiastari.wiss.ui.masyarakat.profile

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dwiastari.wiss.MainActivity
import com.dwiastari.wiss.databinding.FragmentProfileBinding
import com.dwiastari.wiss.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    
    private lateinit var binding: FragmentProfileBinding
    private lateinit var preferences: SharedPreferences
    private val viewModel: ProfileViewModel by viewModels()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    
        preferences = requireContext().getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)
        val username = preferences.getString(Constant.KEY_USERNAME, "")
        val type = preferences.getString(Constant.KEY_TYPE, "")
        viewModel.onLoad(username!!, type!!)
        getProfile()
        
        binding.apply {
            btnLogout.setOnClickListener {
                logout()
            }
            
            btnEditProfile.setOnClickListener {
                startActivity(Intent(requireContext(), EditProfileActivity::class.java))
            }
            
            btnChangePassword.setOnClickListener { startActivity(Intent(requireContext(), EditPasswordActivity::class.java)) }
            
        }
    }
    
    private fun getProfile() {
        viewModel.apply {
            loading.observe(requireActivity()){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
            }
            
            masyarakat.observe(requireActivity()){
                if(it != null){
                    binding.apply {
                        if (it.foto.isNotEmpty()){
                            Glide.with(requireContext())
                                .load(it.foto)
                                .into(ivProfil)
                        }
                        
                        tvName.text = it.nama_masyarakat
                        tvDetail.text = it.domisili
                    }
                }
            }
           
            konselor.observe(requireActivity()){
                if(it != null){
                    binding.apply {
                        if (it.foto.isNotEmpty()){
                            Glide.with(requireContext())
                                .load(it.foto)
                                .into(ivProfil)
                        }
            
                        tvName.text = it.nama_akun
                        tvDetail.text = it.bidang_konselor
                    }
                }
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        getProfile()
    }
    
    private fun logout(){
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
        
        requireActivity().finish()
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }
}