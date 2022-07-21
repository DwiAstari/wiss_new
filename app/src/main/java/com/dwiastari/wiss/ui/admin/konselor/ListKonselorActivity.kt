package com.dwiastari.wiss.ui.admin.konselor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.dwiastari.wiss.adapter.ListKonselorAdapter
import com.dwiastari.wiss.adapter.ListPesanAdapter
import com.dwiastari.wiss.databinding.ActivityListKonselorBinding
import com.dwiastari.wiss.model.Konselor
import com.dwiastari.wiss.model.PesanModel
import com.dwiastari.wiss.ui.masyarakat.pesan.PesanViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListKonselorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListKonselorBinding
    private lateinit var listKonselorAdapter: ListKonselorAdapter
    private val viewModel: PesanViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListKonselorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        listKonselorAdapter = ListKonselorAdapter()
        with(binding){
            rvKonselor.adapter = listKonselorAdapter
            rvKonselor.hasFixedSize()
            
            btnAddKonselor.setOnClickListener{
                startActivity(Intent(this@ListKonselorActivity, AddEditKonselorActivity::class.java))
            }
            
            btnbackL.setOnClickListener { finish() }
            
            etCari.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                
                }
    
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                
                }
    
                override fun afterTextChanged(s: Editable?) {
                    listKonselorAdapter.filterData(s.toString())
                }
    
            })
        }
    
        getKonselor()
    }
    
    private fun getKonselor(){
        with(viewModel){
            getKonselorList()
        
            isLoading.observe(this@ListKonselorActivity){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
            }
        
            listKonselor.observe(this@ListKonselorActivity){
                val listKonselor = arrayListOf<Konselor>()
                it.forEach{ konselor ->
                    listKonselor.add(konselor)
                }
                listKonselorAdapter.setData(listKonselor)
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        getKonselor()
    }
}