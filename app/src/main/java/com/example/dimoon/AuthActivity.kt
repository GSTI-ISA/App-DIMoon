package com.example.dimoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.dimoon.R
import com.example.dimoon.paciente.HomePacienteActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class AuthActivity : AppCompatActivity() {
    private lateinit var user:EditText
    private lateinit var password:EditText
    private lateinit var btnAceptar: Button
    private lateinit var btnCancelar: Button

    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        user  = findViewById(R.id.textViewUser)
        password = findViewById(R.id.textViewPassword)
        btnAceptar = findViewById(R.id.buttonAceptar)
        auth = Firebase.auth
        setup()

        btnCancelar = findViewById(R.id.buttonCancelar)

    }

    fun setup(){
        btnAceptar.setOnClickListener{
            if(user.text.isNotBlank() && password.text.isNotBlank()){
                auth.signInWithEmailAndPassword(
                    user.text.toString(),
                    password.text.toString()
                ).addOnCompleteListener{task->
                    if (task.isSuccessful){
                        Log.i("INFO","El usuario se ha logueado correctamente")
                        showHome(user.text.toString())
                        user.text.clear()
                        password.text.clear()
                    }else{
                        showAlert("Se ha producido un error autenticando al usuario")
                    }

                }
            }
        }

    }
    private fun showAlert(text:String){// recibe texto
        //builder sirve para construir cosas dificiles con este patron. Es como un constructor pero que ya viene hecho
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle("Error")
            .setMessage(text)
            .setPositiveButton("ACEPTAR",null)// no queremos que haga nada--> null(haciendo que se cierre)

        val dialogo: AlertDialog = builder.create()//lo creamos para luego mostrarlos como Toast
        dialogo.show()
    }
    private fun showHome(email: String){
        val myCol = FirebaseFirestore.getInstance().collection("pacientes")
        myCol.document(email).get().addOnSuccessListener {
            if(it.exists()){
                startActivity(Intent(this, HomePacienteActivity::class.java))

            }
            else{
                startActivity(Intent(this, HomePacienteActivity::class.java))
            }
        }
    }
}