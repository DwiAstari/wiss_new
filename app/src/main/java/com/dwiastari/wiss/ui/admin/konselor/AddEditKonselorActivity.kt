package com.dwiastari.wiss.ui.admin.konselor

import android.Manifest
import android.app.ActionBar
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ActivityAddEditKonselorBinding
import com.dwiastari.wiss.model.Konselor
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

@AndroidEntryPoint
class AddEditKonselorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditKonselorBinding
    private val viewModel: KonselorViewModel by viewModels()
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 3
    private var inputStream: InputStream? = null
    private var filename: String? = null
    
    private var PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditKonselorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val listBidang = arrayOf("Layanan Konseling Pranikah", "Layanan Konseling KB dan Kes. Reproduksi", "Layanan Keluarga Remaja da Remaja", "Layanan " +
                "Konseling Keluarga Lansia dan Lansia", "Pembinaan Usaha Ekonomi Keluarga", "Layanan Konseling Keluarga Balita dan Anak", "Layanan Informasi " +
                "Kependudukan dan KB", "Layanan Konseling Keluarga Harmonis")
    
        val adapter = ArrayAdapter(this, R.layout.item_dropdown_role, listBidang)
        
        val konselor = intent.extras?.getParcelable<Konselor>("konselor")
        
        with(viewModel){
            isLoading.observe(this@AddEditKonselorActivity){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
            }
            
            message.observe(this@AddEditKonselorActivity){
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                if(!it.contains("error")){
                    finish()
                }
            }
        }
        
        with(binding){
            etBidang.setAdapter(adapter)
            
            etNama.setText(konselor?.nama_akun ?: "")
            etUsername.setText(konselor?.username ?: "")
            etBidang.setText(konselor?.bidang_konselor ?: "")
    
            if(konselor != null){
                layoutPassword.visibility = View.GONE
                Glide.with(this@AddEditKonselorActivity)
                    .load(konselor.foto)
                    .into(foto)
                
            } else {
                adjustButton()
            }
            
            foto.setOnClickListener {
                if (!hasPermissions(this@AddEditKonselorActivity, *PERMISSIONS)) {
                    ActivityCompat.requestPermissions(this@AddEditKonselorActivity, PERMISSIONS, REQUEST_ID_MULTIPLE_PERMISSIONS)
                } else {
                    openGalleryForImage()
                }
            }
            
            btnbackL.setOnClickListener { finish() }
            
            btnSave.setOnClickListener {
                val nama = etNama.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val username = etUsername.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val bidang = etBidang.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    
                if(inputStream != null){
                    val requestFoto = RequestBody.create("image/*".toMediaTypeOrNull(), inputStream!!.readBytes())
                    val foto = requestFoto.let { it1 -> MultipartBody.Part.createFormData("foto", filename, it1) }
        
                    loading.visibility = View.VISIBLE
                    if(konselor != null){
                        viewModel.updateKonselor(username, nama, bidang, foto)
                    } else {
                        val password = etPassword.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                        viewModel.createKonselor(username, nama, bidang, password, foto)
                    }
                } else {
                    if(konselor != null){
                        val foto = null;
                        loading.visibility = View.VISIBLE
                        viewModel.updateKonselor(username, nama, bidang, foto)
                    } else {
                        Toast.makeText(this@AddEditKonselorActivity, "Foto belum dipilih", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            
            btnDelete.setOnClickListener {
                viewModel.deleteKonselor(etUsername.text.toString())
            }
        }
    }
    
    fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (context != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission!!) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }
    
    private fun openGalleryForImage() {
        
        selectImageFromGalleryResult.launch("image/*")
        
    }
    
    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.foto.setImageURI(it)
            inputStream = contentResolver.openInputStream(it)
            filename = getPathFromURI(it)
        }
    }
    
    private fun getPathFromURI(uri: Uri?): String {
        var path = ""
        if (contentResolver != null) {
            val cursor = contentResolver.query(uri!!, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        Log.d("path", path)
        return path
    }
    
    private fun adjustButton(){
        binding.btnDelete.visibility = View.GONE
        val currentParams = binding.btnSave.layoutParams as LinearLayout.LayoutParams
        currentParams.weight = 2f
        binding.btnDelete.requestLayout()
    }
}