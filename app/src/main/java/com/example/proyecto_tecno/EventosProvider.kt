package com.example.proyecto_tecno

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_tecno.data.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tu.paquete.network.ApiService


//class MainActivity : AppCompatActivity() {
//
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_main)
////
////        val apiService = RetrofitClient.instance.create(ApiService::class.java)
////
////        // Llamada a la API
////        apiService.getEventos().enqueue(object : Callback<List<Evento>> {
////            override fun onResponse(call: Call<List<Evento>>, response: Response<List<Evento>>) {
////                if (response.isSuccessful) {
////                    val eventos = response.body()
////                    eventos?.forEach { evento ->
////                        Log.d("Evento", "Nombre: ${evento.nombre}, Lugar: ${evento.ubicacion}")
////                    }
////                } else {
////                    Log.e("API Error", "Error: ${response.code()}")
////                }
////            }
////
////            override fun onFailure(call: Call<List<Evento>>, t: Throwable) {
////                Log.e("API Error", "Error: ${t.message}")
////            }
////        })
////    }
////}
//
//









