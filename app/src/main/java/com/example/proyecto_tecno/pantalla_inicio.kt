package com.example.proyecto_tecno

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_tecno.databinding.ActivityMainBinding
import com.example.proyecto_tecno.ui.colorRecyclerView.Color
import com.example.proyecto_tecno.ui.colorRecyclerView.ColorListAdapter




class pantalla_inicio : AppCompatActivity() {
    //private lateinit var binding: ActivityMainBinding

    private val data = mutableListOf<Color>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //val recyclerView = binding.imageView
        //recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter = ColorListAdapter (data)
    }

    private fun initColor() {
        data.add(Color(getString(R.string.red), resources.getColor(R.color.red).toString()))
        data.add(Color(getString(R.string.yellow), resources.getColor(R.color.yellow).toString()))
        data.add(Color(getString(R.string.indigo), resources.getColor(R.color.indigo).toString()))
        data.add(Color(getString(R.string.green), resources.getColor(R.color.green).toString()))
    }
}