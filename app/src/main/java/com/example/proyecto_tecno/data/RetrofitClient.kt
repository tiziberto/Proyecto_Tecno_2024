package com.example.proyecto_tecno.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://localhost/eventAPI/" // Cambia a tu URL local si estás en un dispositivo físico

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Convertidor de JSON
            .build()
    }
}