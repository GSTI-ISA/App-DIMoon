package com.example.dimoon.paciente.juegos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dimoon.R

class PlanetaVerdeActivity : AppCompatActivity() {
    lateinit var startButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planeta_verde)
        startButton = findViewById(R.id.start_quiz_button)
        startButton.setOnClickListener {
            intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
    }
}