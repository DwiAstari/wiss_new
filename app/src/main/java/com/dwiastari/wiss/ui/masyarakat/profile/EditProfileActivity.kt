package com.dwiastari.wiss.ui.masyarakat.profile

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.dwiastari.wiss.databinding.ActivityEditProfileBinding
import com.dwiastari.wiss.utils.Constant
import com.dwiastari.wiss.utils.EmptyTextWatcher
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var preferences: SharedPreferences
    private val viewModel: EditProfileViewModel by viewModels()
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 3
    private var inputStream: InputStream? = null
    private var filename: String? = null
    private lateinit var usertype: String
    
    private var PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        preferences = getSharedPreferences(Constant.SHARED_PREF_NAME, MODE_PRIVATE)
        
        setupLayout()
        
        with(binding){
            btnbackL.setOnClickListener { finish() }
            
            btnChangePhoto.setOnClickListener {
                if (!hasPermissions(this@EditProfileActivity, *PERMISSIONS)) {
                    ActivityCompat.requestPermissions(this@EditProfileActivity, PERMISSIONS, REQUEST_ID_MULTIPLE_PERMISSIONS)
                } else {
                    openGalleryForImage()
                }
            }
            
            btnSave.setOnClickListener {
                if(usertype == "masyarakat"){
                    updateMasyarakat()
                } else {
                    updateKonselor()
                }
            }
        }
        
        with(viewModel){
            isLoading.observe(this@EditProfileActivity){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
            }
            
            message.observe(this@EditProfileActivity){
                Toast.makeText(this@EditProfileActivity, it, Toast.LENGTH_SHORT).show()
                if(it.contains("updated")){
                    finish()
                }
            }
        }
    }
    
    private fun setupLayout(){
        usertype = preferences.getString(Constant.KEY_TYPE, "")!!
        
        if(usertype == "masyarakat"){
            binding.layoutBidang.visibility = View.GONE
        } else {
            binding.layoutUmur.visibility = View.GONE
            binding.layoutDomisili.visibility = View.GONE
            binding.layoutPhone.visibility = View.GONE
        }
        
        with(binding){
            etNama.addTextChangedListener(EmptyTextWatcher(layoutNama, "Nama tidak boleh kosong"))
            edtBidang.addTextChangedListener(EmptyTextWatcher(layoutBidang, "Bidang Konselor tidak boleh kosong"))
            edtDomisili.addTextChangedListener(EmptyTextWatcher(layoutDomisili, "Domisili tidak boleh kosong"))
            edtPhone.addTextChangedListener(EmptyTextWatcher(layoutPhone, "No. HP tidak boleh kosong"))
            edtUmur.addTextChangedListener(EmptyTextWatcher(layoutUmur, "Umur tidak boleh kosong"))
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
            binding.ivProfil.setImageURI(it)
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
    
    private fun updateKonselor(){
        with(binding){
            val nama = etNama.text.toString()
            val bidang = edtBidang.text.toString()
            
            var error = false
            
            if(nama.isEmpty()){
                layoutNama.error = "Nama tidak boleh kosong"
                error = true
            }
            
            if(bidang.isEmpty()){
                layoutBidang.error = "Bidang Konselor tidak boleh kosong"
                error = true
            }
            
            if(error){
                Toast.makeText(this@EditProfileActivity, "Mohon periksa kembali inputan anda", Toast.LENGTH_SHORT).show()
            } else {
                val username = preferences.getString(Constant.KEY_USERNAME, "")
                
                val usernameUpdate = username!!.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val namaUpdate = nama.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val bidangUpdate = bidang.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                
                var foto: MultipartBody.Part? = null
                
                if(inputStream != null){
                    val requestFoto = RequestBody.create("image/*".toMediaTypeOrNull(), inputStream!!.readBytes())
                    foto = requestFoto.let { it1 -> MultipartBody.Part.createFormData("foto", filename, it1) }
                }
                
                viewModel.updateKonselor(usernameUpdate, namaUpdate, bidangUpdate, foto)
            }
        }
    }
    
    private fun updateMasyarakat(){
        with(binding){
            val nama = etNama.text.toString()
            val umur = edtUmur.text.toString()
            val hp = edtPhone.text.toString()
            val domisili = edtDomisili.text.toString()
        
            var error = false
        
            if(nama.isEmpty()){
                layoutNama.error = "Nama tidak boleh kosong"
                error = true
            }
        
            if(umur.isEmpty()){
                layoutUmur.error = "Bidang Konselor tidak boleh kosong"
                error = true
            }
    
            if(hp.isEmpty()){
                layoutPhone.error = "Nomor HP tidak boleh kosong"
                error = true
            }
    
            if(umur.isEmpty()){
                layoutDomisili.error = "Domisili tidak boleh kosong"
                error = true
            }
        
            if(error){
                Toast.makeText(this@EditProfileActivity, "Mohon periksa kembali inputan anda", Toast.LENGTH_SHORT).show()
            } else {
                val username = preferences.getString(Constant.KEY_USERNAME, "")
            
                val usernameUpdate = username!!.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val namaUpdate = nama.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val umurUpd = umur.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val hpUpd = hp.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val domisiliUpd = domisili.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            
                var foto: MultipartBody.Part? = null
            
                if(inputStream != null){
                    val requestFoto = RequestBody.create("image/*".toMediaTypeOrNull(), inputStream!!.readBytes())
                    foto = requestFoto.let { it1 -> MultipartBody.Part.createFormData("foto", filename, it1) }
                }
            
                viewModel.updateMasyarakat(usernameUpdate, namaUpdate, umurUpd, hpUpd, domisiliUpd, foto)
            }
        }
    }
}