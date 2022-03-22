package com.dwiastari.wiss.api

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl( "https://api.keluargaku.or.id/CRUD/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun  getInstance() : MasyarakatService{
        return getRetrofitClient().create(MasyarakatService::class.java)
    }

}