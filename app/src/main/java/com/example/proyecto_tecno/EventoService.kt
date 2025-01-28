package com.example.proyecto_tecno

import retrofit2.http.GET

interface EventoService {
    @GET(Constants.PATH_EVENTS)
    suspend fun getEventos() : List<Evento>
}