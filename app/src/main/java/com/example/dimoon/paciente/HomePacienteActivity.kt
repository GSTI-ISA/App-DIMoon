package com.example.dimoon.paciente

import android.R.id.button1
import android.R.id.button2
import android.R.id.button3
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.dimoon.R
import com.example.dimoon.paciente.juegos.PlanetaMoradoActivity
import com.example.dimoon.paciente.juegos.PlanetaRojoActivity
import com.example.dimoon.paciente.juegos.PlanetaRosaActivity
import com.example.dimoon.paciente.juegos.PlanetaVerdeActivity
import kotlin.properties.Delegates


class HomePacienteActivity : BasePaciente() {


    //private lateinit var btnPerfilPaciente:Button
    private lateinit var btnPlanet1: ImageButton
    private lateinit var btnPlanet2: ImageButton
    private lateinit var btnPlanet3: ImageButton
    private lateinit var btnPlanet4: ImageButton
    private lateinit var imageDidi: ImageView

    private lateinit var email: String

    private var planet1Enabled = true
    private var planet2Enabled = false
    private var planet3Enabled = false
    private var planet4Enabled = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_paciente)

        //AÑADIR MENU
        val toolbar: Toolbar = findViewById(R.id.tooolbar_main)
        setSupportActionBar(toolbar)

        setupDrawer(toolbar)
        //----------------------------------------------------


        //PLANETA VERDE FINISH--> BOTON 2 Y 3 HABILITADOS
        val enableButton2 = intent.getBooleanExtra("enableButton2", false)
        val enableButton3 = intent.getBooleanExtra("enableButton3", false)
        val enableButton4 = intent.getBooleanExtra("enableButton4", false)



        //email = intent.getStringExtra("user") ?: ""//email paciente

        //btnPerfilPaciente = findViewById(R.id.btnPerfil)
        btnPlanet1 = findViewById(R.id.btnPlanet1)
        btnPlanet2 = findViewById(R.id.btnPlanet2)
        btnPlanet3 = findViewById(R.id.btnPlanet3)
        btnPlanet4 = findViewById(R.id.btnPlanet4)
        imageDidi = findViewById(R.id.imageViewDidi)



        //DESHABILITAMOS
        disableButton(btnPlanet2)
        disableButton(btnPlanet3)
        disableButton(btnPlanet4)

        btnPlanet1.setOnClickListener {

            if (planet1Enabled) {
                startActivity(Intent(this, PlanetaRojoActivity::class.java))
                planet2Enabled = true
                Handler(Looper.getMainLooper()).postDelayed({
                    enableButton(btnPlanet2)
                }, 2000)

            }
        }

        btnPlanet2.setOnClickListener {

            if (planet2Enabled) {
                startActivity(Intent(this, PlanetaVerdeActivity::class.java))
                planet3Enabled = true
                Handler(Looper.getMainLooper()).postDelayed({
                    enableButton(btnPlanet3)
                }, 2000)
            }
        }

        btnPlanet3.setOnClickListener {
            if (planet3Enabled) {
                startActivity(Intent(this, PlanetaMoradoActivity::class.java))
                planet4Enabled = true
                Handler(Looper.getMainLooper()).postDelayed({
                    enableButton(btnPlanet4)
                }, 2000)
            }


        }

        btnPlanet4.setOnClickListener {
            intent = Intent(this, PlanetaRosaActivity::class.java)
            startActivity(intent)
        }

        //habilitar botones despues de planeta verde y morado

        if (enableButton2) {
            enableButton(btnPlanet2)
            planet2Enabled= true
        }

        if (enableButton3) {
            enableButton(btnPlanet3)
            planet3Enabled = true
        }
        if (enableButton4) {
            enableButton(btnPlanet4)
            planet4Enabled = true
        }
        //---------------------------------------


    }
    private fun disableButton(button: ImageButton) {
        button.isEnabled = false
        button.setColorFilter(Color.argb(128, 0, 0, 0)) // Cambia el color de fondo al deshabilitar
    }

    private fun enableButton(button: ImageButton) {
        button.isEnabled = true
        button.clearColorFilter()
    }






}


