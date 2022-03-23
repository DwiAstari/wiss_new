//package com.dwiastari.wiss.ui.masyarakat.video
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.dwiastari.wiss.model.Video
//import com.dwiastari.wiss.repository.MasyarakatRepository
//import com.dwiastari.wiss.utils.Resource
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class MasyarakatVideoViewModel @Inject constructor(
//    var repository: MasyarakatRepository
//) : ViewModel(){
//
//    val listVideo = MutableLiveData<ArrayList<Video>>()
//
//    fun onLoad() {
//        viewModelScope.launch {
//            var videoResponse: ArrayList<Video> = arrayListOf()
//            when (val response = repository.getVideo() ) {
//                is Resource.Success -> {
//                    response.data?.video?.forEach {
//                        videoResponse.add(it)
//                    }
//                    is Resource.Error -> {
//                        print("error")
//                    }
//                }
//            }
//        }
//    }
//}