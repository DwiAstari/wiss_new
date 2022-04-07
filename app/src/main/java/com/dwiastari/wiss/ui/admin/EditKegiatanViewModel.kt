package com.dwiastari.wiss.ui.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditKegiatanViewModel @Inject constructor(
    var repository: MasyarakatRepository
): ViewModel() {
    
    val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message
    
    fun deleteArticle(id_artikel: String){
        viewModelScope.launch {
            when(val response = repository.deleteArticle(id_artikel)){
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