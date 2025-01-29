import android.app.Application
import androidx.room.Room
import com.example.proyecto_tecno.EventoDataBase

class FinditApplication : Application() {

    companion object {
        lateinit var dataBase: EventoDataBase
    }

    override fun onCreate() {
        super.onCreate()

        // Asegúrate de inicializar la base de datos correctamente
        dataBase = Room.databaseBuilder(
            applicationContext,  // Usar applicationContext
            EventoDataBase::class.java, "FindItDatabase"
        ).build()
    }
}