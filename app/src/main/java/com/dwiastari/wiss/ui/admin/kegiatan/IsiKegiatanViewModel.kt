package com.dwiastari.wiss.ui.admin.kegiatan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class IsiKegiatanViewModel @Inject constructor(
    private val repository: MasyarakatRepository
): ViewModel() {
    
    val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message
    
    fun addArticle(judul: RequestBody, tanggal: RequestBody, isi: RequestBody, area: RequestBody, penulis: RequestBody, status: RequestBody, foto:
    MultipartBody.Part){
        viewModelScope.launch {
            when(val response = repository.createArticle(judul, tanggal, isi, area, penulis, status, foto)){
                is Resource.Success -> {
                    _message.value = response.data?.message
                }
    
                is Resource.Error -> {
                    _message.value = "error"
                }
            }
        }
    }
}