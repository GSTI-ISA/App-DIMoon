package com.example.dimoon.administrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.dimoon.AuthActivity
import com.example.dimoon.InicioActivity
import com.example.dimoon.R
import com.example.dimoon.medico.HomeMedicoActivity
import com.example.dimoon.paciente.HomePacienteActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {
    private lateinit var user: EditText
    private lateinit var password: EditText
    private lateinit var btnAceptar: Button
    private lateinit var btnCancelar: Button
    private lateinit var checkPaciente: CheckBox
    private lateinit var checkMedico: CheckBox
    private lateinit var checkTutor: CheckBox

    private lateinit var btnGenerar: Button
    private lateinit var checkPassword: CheckBox


    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        user = findViewById(R.id.textViewUser)
        password = findViewById(R.id.textViewPassword)
        btnAceptar = findViewById(R.id.buttonAceptar)
        checkPaciente = findViewById(R.id.checkBoxPaciente)
        checkMedico = findViewById(R.id.checkBoxMedico)
        checkTutor = findViewById(R.id.checkBoxTutora)
        btnGenerar = findViewById(R.id.buttonGenerator)
        checkPassword = findViewById(R.id.checkBoxPassword)

        val passwordGenerator = PasswordGenerator()
        btnGenerar.setOnClickListener {
            val passwordG = passwordGenerator.generatePassword(4, "di")
            password.setText(passwordG)
        }


        auth = Firebase.auth
        setup()

        btnCancelar = findViewById(R.id.buttonCancelar)


        btnCancelar.setOnClickListener {
            user.text.clear()
            password.text.clear()
            onClick()
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)

        }

        checkPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Mostrar contraseña
                password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                // Ocultar
                password.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }


    }


    fun setup() {
        // Aquí pondremos la lógica de los botones de autenticación
        btnAceptar.setOnClickListener {
            // Comprobar que el correo electrónico y la contraseña no estén vacíos
            if (user.text.isNotBlank() && password.text.isNotBlank()) {
                // sí podemos registrar al usuario
                auth.createUserWithEmailAndPassword(
                    user.text.toString(),
                    password.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // El registro se ha hecho de forma correcta
                        //crear en la coleccion pacientes de la BD un nuevo documuento con el id del email
                        val myCol = when {
                            checkPaciente.isChecked -> {
                                FirebaseFirestore.getInstance()
                                    .collection("Pacientes").document(user.text.toString())
                                    .set(mapOf("nombre" to ""))
                                val intent = Intent(this, RegistroPacienteActivity::class.java)
                                intent.putExtra("user", user.text.toString())
                                startActivity(intent)
                            }

                            checkMedico.isChecked -> {
                                FirebaseFirestore.getInstance()
                                    .collection("Medicos").document(user.text.toString())
                                    .set(mapOf("nombre" to ""))

                                val intent = Intent(this, RegistroMedicoActivity::class.java)
                                intent.putExtra("user", user.text.toString())
                                startActivity(intent)

                            }

                            checkTutor.isChecked -> FirebaseFirestore.getInstance()
                                .collection("Tutores").document(user.text.toString())
                                .set(mapOf("nombre" to ""))

                            else -> {
                                showAlert("Por favor, seleccione un tipo de usuario")
                                return@addOnCompleteListener
                            }

                        }

                        showAlert("El usuario se ha registrado correctamente")

                        user.text.clear()
                        password.text.clear()
                        onClick()

                        showActivity(user.text.toString())


                    } else {
                        showAlert("Ha habido un fallo en el registro")
                    }
                }

            }
        }


    }

    fun onClick() {

        if (checkTutor.isChecked()) {
            checkTutor.isChecked = false
        } else if (checkPaciente.isChecked()) {
            checkPaciente.isChecked = false
        } else if (checkMedico.isChecked()) {
            checkMedico.isChecked = false
        }

    }


    private fun showAlert(text: String) {// recibe texto
        //builder sirve para construir cosas dificiles con este patron. Es como un constructor pero que ya viene hecho
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle("Información")
            .setMessage(text)
            .setPositiveButton(
                "ACEPTAR",
                null
            )// no queremos que haga nada--> null(haciendo que se cierre)

        val dialogo: AlertDialog = builder.create()//lo creamos para luego mostrarlos como Toast
        dialogo.show()
    }

    private fun showActivity(email: String) {
        if (checkPaciente.isChecked) {
            startActivity(Intent(this, RegistroPacienteActivity::class.java))

        } else if (checkMedico.isChecked) {
            startActivity(Intent(this, RegistroMedicoActivity::class.java))

        } else {
            //Toast.makeText(this, "Elige un rol", Toast.LENGTH_SHORT).show()


        }
    }


}

