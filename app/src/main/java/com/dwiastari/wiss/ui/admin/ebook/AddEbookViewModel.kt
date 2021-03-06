package com.dwiastari.wiss.ui.admin.ebook

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
class AddEbookViewModel @Inject constructor(
    val repository: MasyarakatRepository
): ViewModel() {
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading
    
    private val _response = MutableLiveData<DefaultResponse>()
    val response: LiveData<DefaultResponse>
        get() =  _response
    
    fun createEbook(judul: String, link: String){
        _loading.value = true
        viewModelScope.launch {
            when(val response = repository.addEbook(judul, link)){
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
    
    fun updateEbook(id:String, judul: String, link: String){
        _loading.value = true
        viewModelScope.launch {
            when(val response = repository.updateEbook(id, judul, link)){
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