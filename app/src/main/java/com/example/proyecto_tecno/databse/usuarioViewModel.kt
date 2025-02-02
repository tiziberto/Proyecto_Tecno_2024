import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyecto_tecno.database.UsuarioDao
import com.example.proyecto_tecno.models.UsuarioEntity
import kotlinx.coroutines.launch

class UsuarioViewModel(private val repository: UsuarioDao) : ViewModel() {

    fun getUsuarioByEmail(email: String): LiveData<UsuarioEntity?> {
        val usuarioLiveData = MutableLiveData<UsuarioEntity?>()
        viewModelScope.launch {
            val usuario = repository.getUsuarioByEmail(email)
            usuarioLiveData.postValue(usuario)
        }
        return usuarioLiveData
    }
}
