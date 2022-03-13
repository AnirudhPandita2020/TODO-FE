package com.anirudh.todo_fe.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Time
import java.util.concurrent.TimeUnit

class RetrofitInstance(private val tokenType:String,private val access_token:String){
    companion object{
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().callTimeout(30,TimeUnit.SECONDS).apply {
            this.addInterceptor(interceptor)
        }.build()
        val BaseUrl = "https://todo-fastapi-be.herokuapp.com/"
        fun getInstance(): Retrofit{
            return Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}