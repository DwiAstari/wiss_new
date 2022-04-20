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
    
    
    suspend fun deleteArticle(id_artikel: String) : Resource<DefaultResponse>{
        masyarakatService.deleteArticle(id_artikel).let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun createArticle(judul: RequestBody, tanggal: RequestBody, isi: RequestBody, area: RequestBody, penulis: RequestBody, status: RequestBody, foto:
        MultipartBody.Part): Resource<DefaultResponse>{
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
    
    suspend fun updateLayanan(id_hari: String, layanan: String): Resource<DefaultResponse>{
        masyarakatService.updateLayanan(id_hari, layanan).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return Resource.Success(it)
                }
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
    
    suspend fun updateSlide(id_slide: String, judul_slide: String, status_slide: String): Resource<DefaultResponse>{
        masyarakatService.updateSlides(id_slide, judul_slide, status_slide).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return Resource.Success(it)
                }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun deleteSlide(id_slide: String) : Resource<DefaultResponse>{
        masyarakatService.deleteSlides(id_slide).let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun addSlide(judul: RequestBody, status: RequestBody, foto: MultipartBody.Part): Resource<DefaultResponse>{
        masyarakatService.createSlides(judul, status, foto).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return Resource.Success(it)
                }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun getVideo() : Resource<VideoResponse>{
        masyarakatService.getVideo().let {
            if (it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun deleteVideo(id_video: String) : Resource<DefaultResponse>{
        masyarakatService.deleteVideo(id_video).let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun addVideo(judul: String, link: String): Resource<DefaultResponse>{
        masyarakatService.createVideo(judul, link).let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun updateVideo(id: String, judul: String, link: String): Resource<DefaultResponse>{
        masyarakatService.updateVideo(id, judul, link).let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun getEbook() : Resource<EbookResponse>{
        masyarakatService.getEbook().let {
            if (it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun deleteEbook(id_ebook: String) : Resource<DefaultResponse>{
        masyarakatService.deleteEbook(id_ebook).let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun addEbook(judul: String, link: String): Resource<DefaultResponse>{
        masyarakatService.createEbook(judul, link).let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun updateEbook(id: String, judul: String, link: String): Resource<DefaultResponse>{
        masyarakatService.updateEbook(id, judul, link).let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
}