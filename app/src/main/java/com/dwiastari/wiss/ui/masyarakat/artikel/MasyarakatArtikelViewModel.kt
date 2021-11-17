package com.dwiastari.wiss.ui.masyarakat.artikel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.model.Artikel
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasyarakatArtikelViewModel @Inject constructor(
    var repository: MasyarakatRepository
) : ViewModel(){

    //define list of the data that has been fethed from API
    val listArticle : ArrayList<Artikel> = arrayListOf()

    fun onLoad() {
        viewModelScope.launch {
            when (val response = repository.getArticle() ) {
                is Resource.Success -> {
                    response.data?.artikel?.forEach {
                        listArticle.add(it)
                    }
                }
                is Resource.Error -> {
                    print("error")
                    //action.postValue(LoginViewModel.ACTION_LOGIN_ERROR)
                }
            }
        }
    }
}