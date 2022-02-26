package com.dwiastari.wiss.api

import com.dwiastari.wiss.model.ArtikelResponse
import com.dwiastari.wiss.model.LoginAdminResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface MasyarakatService{
    @GET("artikel/data_artikel.php")
    suspend fun getArticle() : Response<ArtikelResponse>

    @POST("login_admin/loginadmin.php")
    suspend fun loginAdmin(post_username : String, post_password : String) : Response<LoginAdminResponse>
}