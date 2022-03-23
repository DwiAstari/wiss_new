package com.dwiastari.wiss.repository

import com.dwiastari.wiss.api.MasyarakatService
import com.dwiastari.wiss.model.*
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

    suspend fun getLayanan() : Resource<LayananResponse>{
        masyarakatService.getLayanan().let {
            if (it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }

    suspend fun getSlide() : Resource<SlideResponse>{
        masyarakatService.getSlide().let {
            if (it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
}