package com.dwiastari.wiss.ui.masyarakat.pesan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.model.Konselor
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PesanViewModel @Inject constructor(
    private val repository: MasyarakatRepository
): ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val listKonselor = MutableLiveData<List<Konselor>>()
    
    fun getKonselorList(){
        isLoading.value = true
        val konselorResponse = arrayListOf<Konselor>()
        viewModelScope.launch {
            when (val response = repository.getKonselor()){
                is Resource.Success -> {
                    isLoading.value = false
                    response.data?.akun_konselor?.forEach {
                        konselorResponse.add(it)
                    }
                    listKonselor.value = konselorResponse
                }
                
                is Resource.Error -> {
                    isLoading.value = false
                    print("error")
                }
            }
        }
    }
}