package com.example.proyecto_tecno

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_tecno.adapter.EventosAdapter
import com.example.proyecto_tecno.databinding.ActivityPantallaHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PantallaHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaHomeBinding
    //private var eventosList: MutableList<Evento> = EventosProvider.EventosList.toMutableList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)
        replaceFragment(HomeFragment())
       binding.bottomNavigation.setOnItemSelectedListener {
           when(it.itemId){
               R.id.home -> replaceFragment(HomeFragment())
               R.id.profile -> replaceFragment(ProfileFragment())
               R.id.favorites -> replaceFragment(FavoritesFragment())

               else ->{
               }
           }
           true

       }

    }


    override fun onBackPressed() {
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}

