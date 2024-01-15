package com.example.dimoon.medico


import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.dimoon.R
import com.example.dimoon.paciente.BasePaciente
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class PerfilMedicoActivity : BasePaciente() {

    private lateinit var fotoPerfilPaciente: ImageView
    private lateinit var textNombre: TextView
    private lateinit var textEspecialidad: TextView

    private lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_medico)

        //AÑADIR MENU
        val toolbar: Toolbar = findViewById(R.id.tooolbar_main)
        setSupportActionBar(toolbar)

        setupDrawer(toolbar)
        //----------------------------------------------------

        fotoPerfilPaciente = findViewById(R.id.ivPacienteClick)


        textNombre = findViewById(R.id.editTextNombre)
        textEspecialidad = findViewById(R.id.editTextEspecialidad)

        val user = Firebase.auth.currentUser // Acceder al usuario autenticado ahora mismo
        email = user?.email.toString()

        mostrarInfoPac()


    }

    fun mostrarInfoPac() {
        val myCol = FirebaseFirestore.getInstance().collection("Medicos")
        // Recuperar un document a partir de su ID:
        val myDoc = myCol.document(email)
        myDoc.get().addOnSuccessListener {

            var nombre = if (it.get("nombre").toString() != "null") it.get("nombre")
                .toString() else ""//si en la base es distinto de null se coge sino cadena vacia
            var especialidad = if (it.get("especialidad").toString() != "null") it.get("especialidad")
                .toString() else ""

            textNombre.setText(nombre)
            textEspecialidad.setText(especialidad)



            var foto = if (it.get("foto").toString() != "null") it.get("foto").toString() else ""
            //showAlert("$foto")

            Glide.with(this).load(foto).into(fotoPerfilPaciente)




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


}