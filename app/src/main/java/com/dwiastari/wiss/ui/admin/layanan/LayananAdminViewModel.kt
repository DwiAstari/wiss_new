package com.dwiastari.wiss.ui.admin.layanan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.model.Layanan
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LayananAdminViewModel @Inject constructor(
    private val repository: MasyarakatRepository
): ViewModel() {
    
    //define list of the data that has been fethed from API
    val listLayanan = MutableLiveData<ArrayList<Layanan>>()
    
    fun onLoad() {
        viewModelScope.launch {
            var layananResponse: ArrayList<Layanan> = arrayListOf()
            when (val response = repository.getLayanan() ) {
                is Resource.Success -> {
                    response.data?.layanan?.forEach {
                        layananResponse.add(it)
                    }
                    listLayanan.postValue(layananResponse)
                }
                is Resource.Error -> {
                    print("error")
                    //action.postValue(LoginViewModel.ACTION_LOGIN_ERROR)
                }
            }
        }
    }
}