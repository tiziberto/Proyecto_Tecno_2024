import android.app.Application
import androidx.room.Room
import com.example.proyecto_tecno.databse.FindItDataBase

class FinditApplication : Application() {

    companion object {
        lateinit var dataBase: FindItDataBase
    }

    override fun onCreate() {
        super.onCreate()

        // Asegúrate de inicializar la base de datos correctamente
        dataBase = Room.databaseBuilder(
            applicationContext,  // Usar applicationContext
            FindItDataBase::class.java, "FindItDatabase"
        ).build()
    }
}