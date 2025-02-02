package com.example.proyecto_tecno.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_tecno.database.UsuarioRepository
import com.example.proyecto_tecno.models.UsuarioEntity
import kotlinx.coroutines.launch

class UsuarioViewModel(private val repository: UsuarioRepository) : ViewModel() {

    suspend fun getUsuarioByEmailAndPassword(email: String, password: String): UsuarioEntity? {
        return repository.getUsuarioByEmailAndPassword(email, password)
    }
}
