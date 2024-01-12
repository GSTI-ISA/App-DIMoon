package com.example.dimoon.paciente.juegos

import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.example.dimoon.R
import com.example.dimoon.paciente.HomePacienteActivity

class PlanetaVerdeActivity : AppCompatActivity() {
    private lateinit var btnBanana : ImageButton
    private lateinit var btnPera : ImageButton
    private lateinit var btnSandia : ImageButton
    private lateinit var btnNaranja : ImageButton
    private lateinit var btnLimon : ImageButton
    private lateinit var btnGranada : ImageButton
    private lateinit var btnHigo : ImageButton
    private lateinit var btnMandarina : ImageButton
    private lateinit var btnArandanos : ImageButton
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planeta_verde)

        btnBanana = findViewById(R.id.bananaImageButton)
        btnPera = findViewById(R.id.peraImageButton)
        btnSandia = findViewById(R.id.sandiaImageButton)
        btnNaranja = findViewById(R.id.naranjaImageButton)
        btnLimon = findViewById(R.id.limonImageButton)
        btnGranada = findViewById(R.id.granadaImageButton)
        btnHigo = findViewById(R.id.higoImageButton)
        btnMandarina = findViewById(R.id.mandarinaImageButton)
        btnArandanos = findViewById(R.id.arandanosImageButton)

        btnBanana.setOnClickListener {
            sonido("banana")
            btnBanana.isEnabled = false
            btnBanana.setColorFilter(android.graphics.Color.GRAY)
            finJuego()
        }
        btnPera.setOnClickListener {
            sonido("pera")
            btnPera.isEnabled = false
            btnPera.setColorFilter(android.graphics.Color.GRAY)
            finJuego()
        }
        btnSandia.setOnClickListener {
            sonido("sandia")
            btnSandia.isEnabled = false
            btnSandia.setColorFilter(android.graphics.Color.GRAY)
            finJuego()
        }
        btnNaranja.setOnClickListener {
            sonido("naranja")
            btnNaranja.isEnabled = false
            btnNaranja.setColorFilter(android.graphics.Color.GRAY)
            finJuego()
        }
        btnLimon.setOnClickListener {
            sonido("limon")
            btnLimon.isEnabled = false
            btnLimon.setColorFilter(android.graphics.Color.GRAY)
            finJuego()
        }
        btnGranada.setOnClickListener {
            sonido("granada")
            btnGranada.isEnabled = false
            btnGranada.setColorFilter(android.graphics.Color.GRAY)
            finJuego()
        }
        btnHigo.setOnClickListener {
            sonido("higo")
            btnHigo.isEnabled = false
            btnHigo.setColorFilter(android.graphics.Color.GRAY)
            finJuego()
        }
        btnMandarina.setOnClickListener {
            sonido("mandarina")
            btnMandarina.isEnabled = false
            btnMandarina.setColorFilter(android.graphics.Color.GRAY)
            finJuego()
        }
        btnArandanos.setOnClickListener {
            sonido("arandanos")
            btnArandanos.isEnabled = false
            btnArandanos.setColorFilter(android.graphics.Color.GRAY)
            finJuego()
        }
    }

    private fun sonido(sonidoName: String) {
        // Libera recursos si el MediaPlayer ya está inicializado
        mediaPlayer?.release()

        // Crea un nuevo MediaPlayer y carga el sonido desde res/raw
        val resourceId = resources.getIdentifier(sonidoName, "raw", packageName)
        mediaPlayer = MediaPlayer.create(this, resourceId)

        // Inicia la reproducción
        mediaPlayer?.start()
    }

    fun finJuego(){
        if(btnBanana.isEnabled == false &&
            btnPera.isEnabled == false &&
            btnSandia.isEnabled == false &&
            btnNaranja.isEnabled == false &&
            btnLimon.isEnabled == false &&
            btnGranada.isEnabled == false &&
            btnHigo.isEnabled == false &&
            btnMandarina.isEnabled == false &&
            btnArandanos.isEnabled == false){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("ENHORABUENA").setMessage("¡Has aprendido todas las palabras!").setCancelable(false).setPositiveButton("PROXIMA AVENTURA",
                DialogInterface.OnClickListener{ dialogInterface, i->
                    val intent = Intent(this, HomePacienteActivity::class.java)
                    startActivity(intent)
                })
            builder.show()
        }
    }
}