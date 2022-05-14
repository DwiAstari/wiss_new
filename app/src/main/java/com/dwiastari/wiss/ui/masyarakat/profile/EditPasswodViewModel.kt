package com.dwiastari.wiss.ui.masyarakat.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPasswodViewModel @Inject constructor(
    val repository: MasyarakatRepository
): ViewModel() {
    
    val isLoading = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    
    fun changePassword(type: String, username: String, oldPassword: String, newPassword: String){
        isLoading.value = true
        viewModelScope.launch {
            if(type == "masyarakat"){
                when(val response = repository.changePasswordMasyarakat(username, oldPassword, newPassword)){
                    is Resource.Success -> {
                        isLoading.value = false
                        message.value = response.data?.message
                    }
        
                    is Resource.Error -> {
                        isLoading.value = false
                        message.value = "error"
                    }
                }
            } else {
                when(val response = repository.changePasswordKonselor(username, oldPassword, newPassword)){
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
}