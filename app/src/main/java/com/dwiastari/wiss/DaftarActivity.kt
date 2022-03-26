package com.dwiastari.wiss

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dwiastari.wiss.ui.masyarakat.artikel.MasyarakatArtikelViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_daftar.*

@AndroidEntryPoint
class DaftarActivity : AppCompatActivity() {

//    private val viewModel : MasyarakatArtikelViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_daftar)

    btnback_d.setOnClickListener{
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}
}