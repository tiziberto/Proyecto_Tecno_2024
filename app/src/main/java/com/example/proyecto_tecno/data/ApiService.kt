package tu.paquete.network

import com.example.proyecto_tecno.Evento
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("events") // Cambia "events" si tu endpoint tiene un nombre distinto
    fun getEventos(): Call<List<Evento>>
}
