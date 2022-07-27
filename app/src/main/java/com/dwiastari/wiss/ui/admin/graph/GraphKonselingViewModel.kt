package com.dwiastari.wiss.ui.admin.graph

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.model.GraphKonselingResponse
import com.dwiastari.wiss.model.GraphPengunjungResponse
import com.dwiastari.wiss.model.ListYearResponse
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphKonselingViewModel @Inject constructor(
    val repository: MasyarakatRepository
): ViewModel() {
    
    val isLoading = MutableLiveData<Boolean>()
    val listYearResponse = MutableLiveData<ListYearResponse>()
    val graphPayload = MutableLiveData<GraphKonselingResponse>()
    val errorMessage = MutableLiveData<String>()
    
    fun getData(){
        isLoading.value = true
        viewModelScope.launch {
            when (val response = repository.getListYearKonseling()) {
                is Resource.Success -> {
                    isLoading.value = false
                    response.data?.let {
                        listYearResponse.value = it
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
    
    fun getKonseling(year: String){
        isLoading.value = true
        viewModelScope.launch {
            when (val response = repository.getListKonseling(year)) {
                is Resource.Success -> {
                    isLoading.value = false
                    response.data?.let {
                        graphPayload.value = it
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