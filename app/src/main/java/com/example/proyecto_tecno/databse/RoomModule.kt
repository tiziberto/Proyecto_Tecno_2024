import android.app.Application
import androidx.room.Room
import com.example.proyecto_tecno.databse.FindItDataBase

class FinditApplication : Application() {
    companion object {
        lateinit var dataBase: FindItDataBase
    }

    override fun onCreate() {
        super.onCreate()
        dataBase = Room.databaseBuilder(
            applicationContext,
            FindItDataBase::class.java, "FindItDatabase"
        ).build()
    }
}