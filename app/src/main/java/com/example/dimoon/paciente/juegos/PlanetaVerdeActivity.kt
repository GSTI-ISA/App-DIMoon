package com.example.dimoon.paciente.juegos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import com.example.dimoon.R
import com.example.dimoon.paciente.BasePaciente

class PlanetaVerdeActivity : BasePaciente() {
    lateinit var startButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planeta_verde)

        //AÑADIR MENU
        val toolbar: Toolbar = findViewById(R.id.tooolbar_main)
        setSupportActionBar(toolbar)

        setupDrawer(toolbar)
        //----------------------------------------------------



        startButton = findViewById(R.id.start_quiz_button)
        startButton.setOnClickListener {
            intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
    }
}