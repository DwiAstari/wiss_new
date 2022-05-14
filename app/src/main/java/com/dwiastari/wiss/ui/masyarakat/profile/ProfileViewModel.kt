package com.dwiastari.wiss.ui.masyarakat.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.model.AkunKonselor
import com.dwiastari.wiss.model.Masyarakat
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: MasyarakatRepository
): ViewModel() {
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading
    
    val masyarakat = MutableLiveData<Masyarakat>()
    val konselor = MutableLiveData<AkunKonselor>()
    
    fun onLoad(username: String, type: String){
        _loading.value = true
        viewModelScope.launch {
            if(type == "masyarakat"){
                when (val response = repository.getMasyarakat(username)){
                    is Resource.Success -> {
                        _loading.value = false
                        response.data?.payload?.let {
                            masyarakat.value = it
                        }
                    }
        
                    is Resource.Error -> {
                        _loading.value = false
                        print("error")
                    }
                }
            } else {
                when (val response = repository.getAkunKonselor(username)){
                    is Resource.Success -> {
                        _loading.value = false
                        response.data?.payload?.let {
                            konselor.value = it
                        }
                    }
        
                    is Resource.Error -> {
                        _loading.value = false
                        print("error")
                    }
                }
            }
        }
    
    }
}