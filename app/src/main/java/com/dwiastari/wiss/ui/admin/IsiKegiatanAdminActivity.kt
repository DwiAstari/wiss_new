package com.dwiastari.wiss.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ActivityIsiKegiatanAdminBinding
import com.dwiastari.wiss.model.Artikel
import com.dwiastari.wiss.ui.admin.EditKegiatanActivity.Companion.ARTIKEL_EXTRA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_edit_kegiatan.*

@AndroidEntryPoint
class IsiKegiatanAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIsiKegiatanAdminBinding
    private var isEdit = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIsiKegiatanAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val artikel = intent.extras?.getParcelable<Artikel>(ARTIKEL_EXTRA)
        isEdit = artikel != null
        if(isEdit){
            with(binding){
                tvJudul.text = "Edit Kegiatan"
                edtJudul.setText(artikel?.judul_artikel)
                edtTanggal.setText(artikel?.tanggal_artikel)
                edtPenulis.setText(artikel?.penulis)
                edtArea.setText(artikel?.area)
                edtIsi.setText(artikel?.isi_artikel)
            }
        } else {
        
        }
    }
    
}