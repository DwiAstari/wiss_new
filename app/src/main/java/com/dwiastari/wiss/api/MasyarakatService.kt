package com.dwiastari.wiss.api

import com.dwiastari.wiss.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MasyarakatService{
    @GET("artikel/data_artikel.php")
    suspend fun getArticle() : Response<ArtikelResponse>

    @GET("layananharian/data_layanan.php")
    suspend fun getLayanan() : Response<LayananResponse>

    @GET("slide/data_slide.php")
    suspend fun getSlide() : Response<SlideResponse>

    @FormUrlEncoded
    @POST("login_admin/loginadmin.php")
    fun loginAdmin(
        @Field( "post_username") username :String,
        @Field( "post_password") password :String
    ): Call<ResponseLogin>
}