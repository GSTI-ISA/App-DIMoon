package com.example.dimoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.example.dimoon.R
import com.example.dimoon.administrador.RegistroActivity
import com.example.dimoon.administrador.RegistroPacienteActivity
import com.example.dimoon.medico.HomeMedicoActivity
import com.example.dimoon.paciente.HomePacienteActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class AuthActivity : AppCompatActivity() {
    private lateinit var user: EditText
    private lateinit var password: EditText
    private lateinit var btnAceptar: Button
    private lateinit var btnCancelar: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        user = findViewById(R.id.textViewUser)
        password = findViewById(R.id.textViewPassword)
        btnAceptar = findViewById(R.id.buttonAceptar)
        auth = Firebase.auth
        setup()

        btnCancelar = findViewById(R.id.buttonCancelar)

       /* btnCancelar.setOnClickListener{
            intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }*/


    }

    fun setup() {
        btnAceptar.setOnClickListener {
            if (user.text.isNotBlank() && password.text.isNotBlank()) {
                auth.signInWithEmailAndPassword(
                    user.text.toString(),
                    password.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i("INFO", "El usuario se ha logueado correctamente")
                        showHome(user.text.toString())
                        user.text.clear()
                        password.text.clear()
                    } else {
                        showAlert("Se ha producido un error autenticando al usuario")
                    }

                }
            }
        }

    }

    private fun showAlert(text: String) {// recibe texto
        //builder sirve para construir cosas dificiles con este patron. Es como un constructor pero que ya viene hecho
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle("Error")
            .setMessage(text)
            .setPositiveButton(
                "ACEPTAR",
                null
            )// no queremos que haga nada--> null(haciendo que se cierre)

        val dialogo: AlertDialog = builder.create()//lo creamos para luego mostrarlos como Toast
        dialogo.show()
    }


    private fun showHome(email: String) {
        val pacienteCol = FirebaseFirestore.getInstance().collection("Pacientes")
        val medicoCol = FirebaseFirestore.getInstance().collection("Medicos")
        val tutorCol = FirebaseFirestore.getInstance().collection("Tutores")
        val adminCol = FirebaseFirestore.getInstance().collection("Administradores")

        pacienteCol.document(email).get().addOnSuccessListener {
            if (it.exists()) {
                startActivity(Intent(this, HomePacienteActivity::class.java))

            } else {
                medicoCol.document(email).get().addOnSuccessListener { medicoDocument ->
                    if (medicoDocument.exists()) {
                        val intent = Intent(this, HomeMedicoActivity::class.java)
                        intent.putExtra("user", email)
                        startActivity(intent)
                    } else {
                        tutorCol.document(email).get().addOnSuccessListener { TutorDocument ->
                            if (TutorDocument.exists()) {
                                startActivity(Intent(this, HomeMedicoActivity::class.java))

                            } else {
                                adminCol.document(email).get()
                                    .addOnSuccessListener { adminDocument ->
                                        if (adminDocument.exists()) {
                                            startActivity(
                                                Intent(
                                                    this,
                                                    RegistroActivity::class.java
                                                )
                                            )
                                        } else {
                                            showAlert("Usuario no reconocido.")
                                        }

                                    }
                            }
                        }
                    }
                }
            }
        }
    }
}
