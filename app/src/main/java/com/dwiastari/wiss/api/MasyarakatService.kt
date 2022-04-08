package com.dwiastari.wiss.api

import com.dwiastari.wiss.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MasyarakatService{
    @GET("artikel/data_artikel.php")
    suspend fun getArticle() : Response<ArtikelResponse>
    
    @FormUrlEncoded
    @POST("artikel/delete_artikel.php")
    suspend fun deleteArticle(@Field("id_artikel") id_artikel: String) : Response<DeleteArtikelResponse>
    
    @Multipart
    @POST("artikel/create_artikel.php")
    suspend fun createArticle(
        @Part("judul_artikel") judul: RequestBody,
        @Part("tanggal_artikel") tanggal_artikel: RequestBody,
        @Part("isi_artikel") isi_artikel: RequestBody,
        @Part("area") area: RequestBody,
        @Part("penulis") penulis: RequestBody,
        @Part("status") status: RequestBody,
        @Part foto_kegiatan: MultipartBody.Part
    ): Response<DeleteArtikelResponse>

    @GET("layananharian/data_layanan.php")
    suspend fun getLayanan() : Response<LayananResponse>

    @GET("slides/data_slides.php")
    suspend fun getSlide() : Response<SlideResponse>
    
    @FormUrlEncoded
    @POST("loginuser/loginuser.php")
    fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<ResponseLogin>
    
    @FormUrlEncoded
    @POST("loginuser/register.php")
    fun registerUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("age") age: String,
        @Field("address") address: String,
        ): Call<ResponseLogin>
}