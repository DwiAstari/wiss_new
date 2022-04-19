package com.dwiastari.wiss.ui.admin.ebook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.model.DefaultResponse
import com.dwiastari.wiss.model.Ebook
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EbookAdminViewModel @Inject constructor(
    val repository: MasyarakatRepository
): ViewModel() {
    val listEbook = MutableLiveData<ArrayList<Ebook>>()
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading
    
    private val _response = MutableLiveData<DefaultResponse>()
    val response: LiveData<DefaultResponse>
        get() =  _response
    
    fun onLoad() {
        viewModelScope.launch {
            val ebookResponse: ArrayList<Ebook> = arrayListOf()
            when (val response = repository.getEbook() ) {
                is Resource.Success -> {
                    response.data?.ebook?.forEach {
                        ebookResponse.add(it)
                    }
                    listEbook.postValue(ebookResponse)
                }
                is Resource.Error -> {
                    print("error")
                }
            }
        }
    }
    
    fun delete(id_ebook: String){
        _loading.value = true
        viewModelScope.launch {
            when(val response = repository.deleteEbook(id_ebook)){
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