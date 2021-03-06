package com.dwiastari.wiss.ui.admin.layanan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.model.DefaultResponse
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditLayananViewModel @Inject constructor(
    private val repository: MasyarakatRepository
): ViewModel() {
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading
    
    private val _response = MutableLiveData<DefaultResponse>()
    val response: LiveData<DefaultResponse>
        get() =  _response
    
    fun updateLayanan(id: String, layanan: String){
        _loading.value = true
        viewModelScope.launch {
            when (val response = repository.updateLayanan(id, layanan)) {
                is Resource.Success -> {
                    _loading.value = false
                    _response.value = response.data!!
                }
                
                is Resource.Error -> {
                    _loading.value = false
                   print("error")
                }
            }
        }
    }
    
}