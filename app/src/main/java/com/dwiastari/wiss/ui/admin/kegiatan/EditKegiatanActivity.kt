package com.dwiastari.wiss.ui.admin.kegiatan

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ActivityEditKegiatanBinding
import com.dwiastari.wiss.model.Artikel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditKegiatanActivity : AppCompatActivity() {
    
    companion object{
        const val ARTIKEL_EXTRA = "artikel"
    }
    
    private lateinit var binding: ActivityEditKegiatanBinding
    private val viewModel: EditKegiatanViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditKegiatanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val artikel = intent.extras?.getParcelable<Artikel>(ARTIKEL_EXTRA)
        
        with(binding){
            btnbackL.setOnClickListener{finish()}
    
            Glide.with(this@EditKegiatanActivity)
                .load(artikel?.foto_kegiatan)
                .placeholder(R.color.background)
                .centerCrop()
                .into(imgArtikel)
            
            judulartikel.text = artikel?.judul_artikel
            tanggalartikel.text = artikel?.tanggal_artikel
            penulis.text = artikel?.penulis
            isiartikel.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(artikel?.isi_artikel, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(artikel?.isi_artikel)
            }
            
            btnDelete.setOnClickListener{
                val dialog = AlertDialog.Builder(this@EditKegiatanActivity)
                dialog.setMessage("Apakah anda yakin akan menghapus artikel ini?")
                dialog.setPositiveButton("Ya"){ _, _->
                    artikel?.id_artikel?.let { it1 -> viewModel.deleteArticle(it1) }
                }
                dialog.setNegativeButton("Tidak"){alertDialog, _ ->
                    alertDialog.dismiss()
                }
                dialog.show()
            }
            
            btnEdit.setOnClickListener{
                val intent = Intent(this@EditKegiatanActivity, IsiKegiatanAdminActivity::class.java)
                intent.putExtra(ARTIKEL_EXTRA, artikel)
                startActivity(intent)
            }
        }
        
        viewModel.message.observe(this){
            if(it != null){
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                if(it.contains("updated")){
                    finish()
                }
            }
        }
        
//        Toast.makeText(this, artikel?.judul_artikel, Toast.LENGTH_SHORT).show()
    }
}