package com.example.dimoon.administrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.dimoon.R
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat

private lateinit var nombrePacR: EditText
private lateinit var especialidadPacR: EditText

private lateinit var buttonRegistro: Button
private lateinit var buttonCancelarRegistro: Button
private lateinit var email: String

private lateinit var foto: EditText
class RegistroMedicoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_medico)


        nombrePacR = findViewById(R.id.editTextNombreR)
        especialidadPacR = findViewById(R.id.editTextEspecialidad)
        foto = findViewById(R.id.editTextFto)





        buttonCancelarRegistro = findViewById(R.id.buttonCancelarReg)
        buttonCancelarRegistro.setOnClickListener{
            intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
        buttonRegistro = findViewById(R.id.buttonRegistoBD)
        email = intent.getStringExtra("user") ?: ""

        if (email != null) {
            buttonRegistro.setOnClickListener {
                almacenarInfoBD()
                nombrePacR.text.clear()
                especialidadPacR.text.clear()
                foto.text.clear()
                Handler().postDelayed({
                    startActivity(Intent(this, RegistroActivity::class.java))
                    // Aquí puedes realizar cualquier acción adicional después del retraso
                }, 2000)
            }
        } else {
            showAlert("El usuario no se ha registrado correctamente")
        }


    }






    private fun almacenarInfoBD() {
        //accedemos a la BD
        val myBD = FirebaseFirestore.getInstance()
        val myCol = myBD.collection("Medicos")


        var nuevaInfo = mapOf(
            "nombre" to nombrePacR.text.toString(),
            "especialidad" to especialidadPacR.text.toString(),
            "foto" to foto.text.toString()
        )
        val myDoc = myCol.document(email).set(nuevaInfo)
            .addOnSuccessListener {
                Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                showAlert("Error")
            }


    }
    private fun showAlert(text: String) {
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
}