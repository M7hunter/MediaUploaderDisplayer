package com.m7awas.mediauploaderdisplayer.dataSource.Server

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class RetrofitClient private constructor(){

    private val BASE_URL = "http://fesal.rmal.com.sa/task/api/"
    private var retrofit : Retrofit

    init {
        val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    companion object {
        var instance = RetrofitClient()
    }

    fun APIs() : APIs = retrofit.create(APIs::class.java)
}
