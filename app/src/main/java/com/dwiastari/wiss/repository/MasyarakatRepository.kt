package com.dwiastari.wiss.repository

import com.dwiastari.wiss.api.MasyarakatService
import com.dwiastari.wiss.model.*
import com.dwiastari.wiss.utils.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    
    suspend fun deleteArticle(id_artikel: String) : Resource<DeleteArtikelResponse>{
        masyarakatService.deleteArticle(id_artikel).let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun createArticle(judul: RequestBody, tanggal: RequestBody, isi: RequestBody, area: RequestBody, penulis: RequestBody, status: RequestBody, foto:
        MultipartBody.Part): Resource<DeleteArtikelResponse>{
        masyarakatService.createArticle(judul, tanggal, isi, area, penulis, status, foto).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return Resource.Success(it)
                }
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