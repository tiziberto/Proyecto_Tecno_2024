package com.example.proyecto_tecno

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.proyecto_tecno.databinding.ActivityPantallaHomeBinding
import com.example.proyecto_tecno.ui.SettingsFragment

class PantallaHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaHomeBinding

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
               R.id.settings -> replaceFragment(SettingsFragment())
               else ->{
               }
           }
           true

       }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        }

    }

    // esta puesto para que no deje volver para el login (no sacar)
    override fun onBackPressed() {
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}

