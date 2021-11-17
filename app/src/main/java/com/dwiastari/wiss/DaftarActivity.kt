package com.dwiastari.wiss

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dwiastari.wiss.ui.masyarakat.artikel.MasyarakatArtikelViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaftarActivity : AppCompatActivity() {

    private val viewModel : MasyarakatArtikelViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)

        //sebagai actionbar kembali ke main
        val actionBar = supportActionBar
        //set actionbar
        actionBar!!.title = "Keluargaku"
        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
       viewModel.onLoad()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}