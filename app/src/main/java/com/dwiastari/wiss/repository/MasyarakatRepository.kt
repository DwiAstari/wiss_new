package com.dwiastari.wiss.repository

import com.dwiastari.wiss.api.MasyarakatService
import com.dwiastari.wiss.model.ArtikelResponse
import com.dwiastari.wiss.model.LoginAdminResponse
import com.dwiastari.wiss.utils.Resource
import javax.inject.Inject

class MasyarakatRepository @Inject constructor(
    private val masyarakatService: MasyarakatService
) {
    suspend fun getArticle() : Resource<ArtikelResponse>{
        masyarakatService.getArticle().let {
            if (it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }

    suspend fun loginAdmin(post_username : String, post_password : String) : Resource<LoginAdminResponse>{
        masyarakatService.loginAdmin(post_username,post_password).let {
            if (it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
}