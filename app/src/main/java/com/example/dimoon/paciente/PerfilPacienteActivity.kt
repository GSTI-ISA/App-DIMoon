package com.example.dimoon.paciente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.dimoon.R

class PerfilPacienteActivity : AppCompatActivity() {

    private lateinit var fotoPerfilPaciente:ImageView
    private lateinit var btnSignOut: Button
    private lateinit var textApellidos:TextView
    private lateinit var textNombre:TextView
    private lateinit var textFechaNac:TextView
    private lateinit var textDiagnostico:TextView

    private lateinit var apellidos:EditText
    private lateinit var nombre:EditText
    private lateinit var fechaNac:EditText
    private lateinit var diagnostico:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_paciente)

        fotoPerfilPaciente = findViewById(R.id.imageViewFotoPerfilPaciente)
        btnSignOut = findViewById(R.id.btnSignOut)

        textApellidos = findViewById(R.id.textViewApellidos)
        textNombre = findViewById(R.id.textViewName)
        textFechaNac = findViewById(R.id.textViewFechaNac)
        textDiagnostico = findViewById(R.id.textViewDiagnostico)

        apellidos = findViewById(R.id.editTextNombre)
        nombre = findViewById(R.id.editTextApellidos)
        fechaNac = findViewById(R.id.editTextFechaNac)
        diagnostico = findViewById(R.id.editTextDiagnostico)
    }
}