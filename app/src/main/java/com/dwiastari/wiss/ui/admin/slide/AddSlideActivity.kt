package com.dwiastari.wiss.ui.admin.slide

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.dwiastari.wiss.R
import com.dwiastari.wiss.databinding.ActivityAddSlideBinding
import com.dwiastari.wiss.model.Slide
import com.dwiastari.wiss.utils.EmptyTextWatcher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail_slide_admin.*
import kotlinx.android.synthetic.main.activity_isi_kegiatan_admin.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

@AndroidEntryPoint
class AddSlideActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAddSlideBinding
    private val viewModel: AddSlideViewModel by viewModels()
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
        binding = ActivityAddSlideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val slides = intent.extras?.getParcelable<Slide>(DetailSlideAdminActivity.EXTRA_SLIDE)
        isEdit = slides != null
        
        viewModel.apply {
            loading.observe(this@AddSlideActivity){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
            }
            
            response.observe(this@AddSlideActivity){
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                if(it.message != "Error")
                    finish()
            }
        }
        
        binding.apply {
            btnbackL.setOnClickListener { finish() }
            edtJudul.addTextChangedListener(EmptyTextWatcher(layoutJudul, "Isi Judul Slide"))
            binding.btnChoose.setOnClickListener {
                if (!hasPermissions(this@AddSlideActivity, *PERMISSIONS)) {
                    ActivityCompat.requestPermissions(this@AddSlideActivity, PERMISSIONS, REQUEST_ID_MULTIPLE_PERMISSIONS)
                } else {
                    openGalleryForImage()
                }
            }
            
            if(isEdit){
                tvJudul.text = "Edit Slide"
                layoutStatus.visibility = View.VISIBLE
                btnChoose.visibility = View.GONE
                
                val listStatus = arrayOf("Tampil", "Tidak Tampil")
                val adapter = ArrayAdapter<String?>(this@AddSlideActivity, R.layout.item_dropdown_role, listStatus)
                spinnerStatus.setAdapter(adapter)
                
                edtJudul.setText(slides?.judul_slide)
                Glide.with(this@AddSlideActivity)
                    .load(slides?.foto_slides)
                    .into(imgSlides)
                spinnerStatus.setText(if(slides?.status == "Tampil") listStatus[0] else listStatus[1])
                
                btnadd.setOnClickListener {
                    val judul = edtJudul.text.toString()
                    val status = spinnerStatus.text.toString()
                    
                    if(judul.isEmpty()){
                        layoutJudul.error = "Isi Judul Slide"
                        Toast.makeText(this@AddSlideActivity, "Periksa kembali inputan anda", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.updateSlide(slides!!.id_slide, judul, status)
                    }
                }
            } else {
                btnadd.setOnClickListener {
                    val judul = edtJudul.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val status = "Tampil".toRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val requestFoto = RequestBody.create("image/*".toMediaTypeOrNull(), inputStream!!.readBytes())
                    val foto = requestFoto?.let { it1 -> MultipartBody.Part.createFormData("foto_kegiatan", filename, it1) }
    
                    if(foto != null){
                        viewModel.addSlide(judul, status, foto)
                    }
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
            binding.imgSlides.setImageURI(it)
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
}