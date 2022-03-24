package com.dwiastari.wiss.ui.masyarakat.slide

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.model.Slide
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasyarakatSlideViewModel @Inject constructor(
    var repository: MasyarakatRepository
) : ViewModel(){
    
    val listSlide = MutableLiveData<ArrayList<Slide>>()
    
    fun onLoad() {
        viewModelScope.launch { 
            var slideResponse: ArrayList<Slide> = arrayListOf()
            when (val response = repository.getSlide() ) {
                is Resource.Success -> {
                    response.data?.slide?.forEach { 
                        slideResponse.add(it)
                    }
                    listSlide.postValue(slideResponse)
                }
                is Resource.Error -> {
                    print("error")
                }
            }
        }
    }
}