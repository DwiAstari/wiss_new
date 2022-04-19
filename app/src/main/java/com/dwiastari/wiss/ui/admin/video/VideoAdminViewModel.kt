package com.dwiastari.wiss.ui.admin.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.model.DefaultResponse
import com.dwiastari.wiss.model.Video
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoAdminViewModel @Inject constructor(
    val repository: MasyarakatRepository
): ViewModel(){
    
    val listVideo = MutableLiveData<ArrayList<Video>>()
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading
    
    private val _response = MutableLiveData<DefaultResponse>()
    val response: LiveData<DefaultResponse>
        get() =  _response
    
    fun onLoad() {
        viewModelScope.launch {
            val videoResponse: ArrayList<Video> = arrayListOf()
            when (val response = repository.getVideo() ) {
                is Resource.Success -> {
                    response.data?.video?.forEach {
                        videoResponse.add(it)
                    }
                    listVideo.postValue(videoResponse)
                }
                is Resource.Error -> {
                    print("error")
                }
            }
        }
    }
    
    fun delete(id_video: String){
        _loading.value = true
        viewModelScope.launch {
            when(val response = repository.deleteVideo(id_video)){
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