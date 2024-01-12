package com.example.dimoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tiempoEspera = 2000L // 2000 milisegundos = 2 segundos

        // Utilizamos un Handler para agregar una demora y luego iniciar la siguiente actividad
        Handler().postDelayed({
            // Crea un Intent para iniciar la HomePacienteActivity
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
            // Cierra la actividad actual
            finish()
        }, tiempoEspera)
    }
}