package com.example.dimoon.paciente

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.example.dimoon.R
import com.example.dimoon.paciente.juegos.PlanetaMoradoActivity
import com.example.dimoon.paciente.juegos.PlanetaRojoActivity
import com.example.dimoon.paciente.juegos.PlanetaRosaActivity
import com.example.dimoon.paciente.juegos.PlanetaVerdeActivity

class HomePacienteActivity : AppCompatActivity() {
    private lateinit var btnPerfilPaciente:Button
    private lateinit var btnPlanet1:ImageButton
    private lateinit var btnPlanet2:ImageButton
    private lateinit var btnPlanet3:ImageButton
    private lateinit var btnPlanet4:ImageButton
    private lateinit var imageDidi:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_paciente)

        btnPerfilPaciente = findViewById(R.id.btnPerfil)
        btnPlanet1 = findViewById(R.id.btnPlanet1)
        btnPlanet2 = findViewById(R.id.btnPlanet2)
        btnPlanet3 = findViewById(R.id.btnPlanet3)
        btnPlanet4 = findViewById(R.id.btnPlanet4)
        imageDidi = findViewById(R.id.imageViewDidi)

        btnPerfilPaciente.setOnClickListener {
            /*
            // Anima el ImageView desde la posición inicial (0, 0) hasta la posición final (200, 200)
            val animatorX = ObjectAnimator.ofFloat(imageDidi, "translationX", 0f, 200f)
            val animatorY = ObjectAnimator.ofFloat(imageDidi, "translationY", 0f, 200f)

            // Configura la duración de la animación (en milisegundos)
            animatorX.duration = 1000
            animatorY.duration = 1000

            // Inicia las animaciones simultáneamente
            animatorX.start()
            animatorY.start()
             */
            intent = Intent(this, PerfilPacienteActivity::class.java)
            startActivity(intent)
        }

        btnPlanet1.setOnClickListener {
            startActivity(Intent(this, PlanetaRojoActivity::class.java))
        }

        btnPlanet2.setOnClickListener {
            startActivity(Intent(this, PlanetaVerdeActivity::class.java ))
        }

        btnPlanet3.setOnClickListener {
            startActivity(Intent(this, PlanetaMoradoActivity::class.java))
        }

        btnPlanet4.setOnClickListener {
            startActivity(Intent(this, PlanetaRosaActivity::class.java))
        }
    }
}