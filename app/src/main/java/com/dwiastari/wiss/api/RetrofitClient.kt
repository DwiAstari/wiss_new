package com.dwiastari.wiss.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private fun getRetrofitClient(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY;
    
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        
        return Retrofit.Builder()
            .baseUrl( "https://api.keluargaku.or.id/CRUD/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun  getInstance() : MasyarakatService{
        return getRetrofitClient().create(MasyarakatService::class.java)
    }

}