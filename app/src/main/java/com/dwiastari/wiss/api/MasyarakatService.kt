package com.dwiastari.wiss.api

import com.dwiastari.wiss.model.ArtikelResponse
import retrofit2.Response
import retrofit2.http.GET

interface MasyarakatService{
    @GET("artikel/data_artikel.php")
    suspend fun getArticle() : Response<ArtikelResponse>
}