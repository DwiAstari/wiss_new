package com.dwiastari.wiss.ui.admin.konselor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.model.DefaultResponse
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


@HiltViewModel
class KonselorViewModel @Inject constructor(
    val repository: MasyarakatRepository
): ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    
    fun updateKonselor(username: RequestBody, nama: RequestBody, bidang: RequestBody, foto: MultipartBody.Part?) {
        isLoading.value = true
        viewModelScope.launch {
            when(val response = repository.updateKonselor(username, nama, bidang, foto)){
                is Resource.Success -> {
                    isLoading.value = false
                    message.value = response.data?.message
                }
                
                is Resource.Error -> {
                    isLoading.value = false
                    message.value = "error"
                }
            }
        }
    }
    
    fun createKonselor(username: RequestBody, nama: RequestBody, bidang: RequestBody, password: RequestBody, foto: MultipartBody.Part?) {
        isLoading.value = true
        viewModelScope.launch {
            when(val response = repository.createKonselor(username, nama, bidang, password, foto)){
                is Resource.Success -> {
                    isLoading.value = false
                    message.value = response.data?.message
                }
                
                is Resource.Error -> {
                    isLoading.value = false
                    message.value = "error"
                }
            }
        }
    }
}