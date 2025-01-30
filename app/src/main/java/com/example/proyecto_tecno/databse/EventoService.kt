package com.example.proyecto_tecno.databse

import com.example.proyecto_tecno.Constants
import retrofit2.http.GET

interface EventoService {
    @GET(Constants.PATH_EVENTS)
    suspend fun getEventos() : List<EventoEntity>
}