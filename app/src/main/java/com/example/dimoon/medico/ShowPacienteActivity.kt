package com.example.dimoon.medico

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.dimoon.R
import com.example.dimoon.paciente.BasePaciente
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

class ShowPacienteActivity : BasePaciente() {
    private  lateinit var nombrePac: TextView
    private  lateinit var enfermedadPac: TextView
    private  lateinit var fotoPac: ImageView

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var audio1btn : ImageView
    private lateinit var audio2btn : ImageView

    private  lateinit var puntuacionQ: TextView
    private  lateinit var tiempoP: TextView

    private lateinit var emailPaciente: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_paciente)

        //AÑADIR MENU
        val toolbar: Toolbar = findViewById(R.id.tooolbar_main)
        setSupportActionBar(toolbar)

        setupDrawer(toolbar)
        //----------------------------------------------------



        emailPaciente = intent.getStringExtra("emailPaciente") ?: ""//email paciente click

        nombrePac = findViewById(R.id.textViewNombre)
        enfermedadPac = findViewById(R.id.textViewEnfermedad)
        fotoPac = findViewById(R.id.ivPacienteClick)
        puntuacionQ = findViewById(R.id.textViewResTestEmocional)

        tiempoP = findViewById(R.id.textViewTiempoMemoria)

        audio1btn = findViewById(R.id.audio1ImageButton)
        audio2btn = findViewById(R.id.audio2ImageButton)

        audio1btn.setOnClickListener {
            sonido("nino1")
        }
        audio2btn.setOnClickListener {
            sonido("nino2")
        }

        mostrarInfoPaciente()
    }

    private fun mostrarInfoPaciente(){
        val myCol = FirebaseFirestore.getInstance().collection("Pacientes")
        // Recuperar un document a partir de su ID:
        val myDoc = myCol.document(emailPaciente)
        myDoc.get().addOnSuccessListener{
            var nombre = if(it.get("nombre").toString()!="null") it.get("nombre").toString() else ""//si en la base es distinto de null se coge sino cadena vacia
            var apellido = if(it.get("apellidos").toString()!="null") it.get("apellidos").toString() else ""//si en la base es distinto de null se coge sino cadena vacia

            var enfermedad = if(it.get("enfermedad").toString()!="null") it.get("enfermedad").toString() else ""

            var foto = if(it.get("foto").toString()!="null") it.get("foto").toString() else ""

            var quiz = if(it.get("puntuacionQuiz").toString()!="null") it.get("puntuacionQuiz").toString() else ""

            var tiempo = if(it.get("tiempoParejas").toString()!="null") it.get("tiempoParejas").toString() else ""


            nombrePac.setText(nombre+" "+apellido)
            puntuacionQ.setText(quiz)
            enfermedadPac.setText(enfermedad)
            tiempoP.setText(tiempo)
            Glide.with(this).load(foto).into(fotoPac)


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

}