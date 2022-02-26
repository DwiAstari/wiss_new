package com.dwiastari.wiss.ui.masyarakat.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiastari.wiss.repository.MasyarakatRepository
import com.dwiastari.wiss.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginActivityViewModel @Inject constructor(
    private val repository: MasyarakatRepository
) : ViewModel() {

    companion object {
        const val ACTION_LOGIN_SUCCESS = "ACTION_SUCCESS"
        const val ACTION_LOGIN_FAILED = "ACTION_FAILED"
        const val ACTION_LOGIN_ERROR = "ACTION_ERROR"
    }

    val isLoading = MutableLiveData<Boolean>()
    val action = MutableLiveData<String>()

    fun loginAdmin(tvEmail : String?, tvPassword : String?){
        isLoading.value = true

        viewModelScope.launch {
            if (!tvEmail.isNullOrEmpty() && !tvPassword.isNullOrEmpty()) {
                val username = tvEmail
                val password = tvPassword
                when (val response = repository.loginAdmin(username,password)) {
                    is Resource.Success -> {
                        if (response.data?.response == true) {
                                isLoading.postValue(false)
                            action.postValue(ACTION_LOGIN_SUCCESS)
                        } else {
                            isLoading.postValue(false)
                            action.postValue(ACTION_LOGIN_FAILED)
                        }
                    }
                    is Resource.Error -> {
                        isLoading.postValue(false)
                        action.postValue(ACTION_LOGIN_ERROR)
                    }
                }
            } else {
                isLoading.postValue(false)
                action.postValue(ACTION_LOGIN_ERROR)
            }
        }
        }
    }