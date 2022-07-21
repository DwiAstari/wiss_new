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
    
    suspend fun updateArticle(id_artikel: RequestBody, judul: RequestBody, tanggal: RequestBody, isi: RequestBody, area: RequestBody, penulis: RequestBody,
                              status: RequestBody, foto:
        MultipartBody.Part?): Resource<DefaultResponse>{
        masyarakatService.updateArticle(id_artikel, judul, tanggal, isi, area, penulis, status, foto).let {
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
    
    suspend fun getActiveSlide() : Resource<SlideResponse>{
        masyarakatService.getActiveSlide().let {
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
    
    suspend fun getKunjungan(): Resource<UserCountResponse>{
        masyarakatService.getKunjungan().let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun getMasyarakat(username: String): Resource<MasyarakatResponse>{
        masyarakatService.getMasyarakatById(username).let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun getAkunKonselor(username: String): Resource<AkunKonselorResponse>{
        masyarakatService.getKonselorById(username).let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun deleteToken(username: String, token: String): Resource<DefaultResponse>{
        masyarakatService.deleteToken(username, token).let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun getKonselor() : Resource<KonselorResponse>{
        masyarakatService.getKonselor().let {
            if (it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun createKonselor(username: RequestBody, nama: RequestBody, bidang: RequestBody, password: RequestBody, foto: MultipartBody.Part?):
            Resource<DefaultResponse>{
        masyarakatService.createKonselor(username, password, nama, bidang, foto).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return Resource.Success(it)
                }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun updateKonselor(username: RequestBody, nama: RequestBody, bidang: RequestBody, foto: MultipartBody.Part?): Resource<DefaultResponse>{
        masyarakatService.updateKonselor(username, nama, bidang, foto).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return Resource.Success(it)
                }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun deleteKonselor(username: String) : Resource<DefaultResponse>{
        masyarakatService.deleteKonselor(username).let {
            if(it.isSuccessful){
                it.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun updateMasyarakat(username: RequestBody, nama: RequestBody, umur: RequestBody, hp: RequestBody, domisili: RequestBody,  foto: MultipartBody
    .Part?):
            Resource<DefaultResponse>{
        masyarakatService.updateMasyarakat(username, nama, umur, hp, domisili, foto).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return Resource.Success(it)
                }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun changePasswordKonselor(username: String, oldPassword: String, newPassword: String): Resource<DefaultResponse>{
        masyarakatService.changePasswordKonselor(username, oldPassword, newPassword).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return Resource.Success(it)
                }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun changePasswordMasyarakat(username: String, oldPassword: String, newPassword: String): Resource<DefaultResponse>{
        masyarakatService.changePasswordMasyarakat(username, oldPassword, newPassword).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return Resource.Success(it)
                }
            }
            return Resource.Error(it.message())
        }
    }
    
    suspend fun changePasswordAdmin(username: String, oldPassword: String, newPassword: String): Resource<DefaultResponse>{
        masyarakatService.changePasswordAdmin(username, oldPassword, newPassword).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return Resource.Success(it)
                }
            }
            return Resource.Error(it.message())
        }
    }
}