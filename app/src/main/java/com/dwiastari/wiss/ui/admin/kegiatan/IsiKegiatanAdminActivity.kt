package com.dwiastari.wiss.ui.admin.kegiatan

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.dwiastari.wiss.databinding.ActivityIsiKegiatanAdminBinding
import com.dwiastari.wiss.model.Artikel
import com.dwiastari.wiss.ui.admin.kegiatan.EditKegiatanActivity.Companion.ARTIKEL_EXTRA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_isi_kegiatan_admin.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import java.util.*


@AndroidEntryPoint
class IsiKegiatanAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIsiKegiatanAdminBinding
    private val viewModel: IsiKegiatanViewModel by viewModels()
    private var isEdit = false
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
            binding.btnadd.setOnClickListener {
                val judul = edtJudul.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val tanggal = edtTanggal.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val isi = edtIsi.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val penulis = edtPenulis.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val area = edtArea.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val status = "".toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val requestFoto = RequestBody.create("image/*".toMediaTypeOrNull(), inputStream!!.readBytes())
                val foto = requestFoto?.let { it1 -> MultipartBody.Part.createFormData("foto_kegiatan", filename, it1) }
    
                if (foto != null) {
                    viewModel.addArticle(judul, tanggal, isi, area, penulis, status, foto)
                }
            }
        }
        
        binding.edtTanggal.setOnClickListener{
            showDatePicker();
        }
        
        binding.btnChoose.setOnClickListener {
            if (!hasPermissions(this, *PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_ID_MULTIPLE_PERMISSIONS)
            } else {
                openGalleryForImage()
            }
        }
        
        binding.btnbackL.setOnClickListener { finish() }
    
        viewModel.message.observe(this){
            if(it != null){
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                if(it.contains("created")){
                    finish()
                }
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
            binding.inputfotokegiatan.setImageURI(it)
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
    
    private fun showDatePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
    
    
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        
            // Display Selected date in textbox
            val monthVal: String = if(monthOfYear > 9) "${monthOfYear+1}" else "0${monthOfYear+1}"
            binding.edtTanggal.setText("$year-$monthVal-$dayOfMonth")
        
        }, year, month, day)
    
        dpd.show()
    }
    
}