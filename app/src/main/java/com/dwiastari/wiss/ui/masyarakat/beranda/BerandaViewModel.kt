package com.dwiastari.wiss.ui.masyarakat.beranda

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.model.GraphKonselingResponse
import com.dwiastari.wiss.model.GraphPengunjungResponse
import com.dwiastari.wiss.model.Slide
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BerandaViewModel @Inject constructor(
    private val repository: MasyarakatRepository
): ViewModel() {
    
    val listSlide = MutableLiveData<ArrayList<Slide>>()
    val kunjungan = MutableLiveData<Int>()
    val isLoading = MutableLiveData<Boolean>()
    val graphPayloadPengunjung = MutableLiveData<GraphPengunjungResponse>()
    val errorMessage = MutableLiveData<String>()
    
    fun onLoad() {
        isLoading.value = true
        viewModelScope.launch {
            val slideResponse: ArrayList<Slide> = arrayListOf()
            when (val response = repository.getActiveSlide() ) {
                is Resource.Success -> {
                    response.data?.slides?.forEach {
                        slideResponse.add(it)
                    }
                    listSlide.postValue(slideResponse)
                }
                is Resource.Error -> {
                    print("error")
                }
            }
    
            when (val response = repository.getKunjungan() ) {
                is Resource.Success -> {
                    response.data?.total?.let {
                        kunjungan.value = it.toInt()
                    }
                }
                is Resource.Error -> {
                    print("error")
                }
            }
            
            isLoading.value = false
        }
    }
    
    fun getKunjungan(year: String){
        isLoading.value = true
        viewModelScope.launch {
            when (val response = repository.getListKunjungan(year)) {
                is Resource.Success -> {
                    isLoading.value = false
                    response.data?.let {
                        graphPayloadPengunjung.value = it
                    }
                }
                
                is Resource.Error -> {
                    isLoading.value = false
                    response.message?.let {
                        errorMessage.value = it
                    }
                }
            }
        }
    }
}