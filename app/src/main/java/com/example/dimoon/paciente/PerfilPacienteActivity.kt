package com.example.dimoon.paciente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.dimoon.Paciente
import com.example.dimoon.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.log

class PerfilPacienteActivity : BasePaciente() {

    private lateinit var fotoPerfilPaciente: ImageView
    private lateinit var textApellidos: TextView
    private lateinit var textNombre: TextView
    private lateinit var textFechaNac: TextView
    private lateinit var textDiagnostico: TextView
    private lateinit var textTutor: TextView

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_paciente)

        //AÑADIR MENU
        val toolbar: Toolbar = findViewById(R.id.tooolbar_main)
        setSupportActionBar(toolbar)

        setupDrawer(toolbar)
        //----------------------------------------------------

        fotoPerfilPaciente = findViewById(R.id.ivPacienteClick)


        textApellidos = findViewById(R.id.editTextApellidos)
        textNombre = findViewById(R.id.editTextNombre)
        textFechaNac = findViewById(R.id.editTextFechaNac)
        textDiagnostico = findViewById(R.id.editTextDiagnostico)
        textTutor = findViewById(R.id.editTextTutorLegal)

        val user = Firebase.auth.currentUser // Acceder al usuario autenticado ahora mismo
        email = user?.email.toString()

        mostrarInfoPac()


    }

    fun mostrarInfoPac() {
        val myCol = FirebaseFirestore.getInstance().collection("Pacientes")
        // Recuperar un document a partir de su ID:
        val myDoc = myCol.document(email)
        myDoc.get().addOnSuccessListener {

            var nombre = if (it.get("nombre").toString() != "null") it.get("nombre")
                .toString() else ""//si en la base es distinto de null se coge sino cadena vacia
            var apellido = if (it.get("apellidos").toString() != "null") it.get("apellidos")
                .toString() else ""//si en la base es distinto de null se coge sino cadena vacia

            textNombre.setText(nombre)
            textApellidos.setText(apellido)

            var fechaNacimiento = it.getTimestamp("fecha de nacimiento")
            var fecha = fechaNacimiento?.toDate()
            var formato = SimpleDateFormat("dd/MM/yyyy")
            var fechastr = formato.format(fecha)

            textFechaNac.setText(fechastr)

            var enfermedad = if (it.get("enfermedad").toString() != "null") it.get("enfermedad")
                .toString() else ""

            var foto = if (it.get("foto").toString() != "null") it.get("foto").toString() else ""

            textDiagnostico.setText(enfermedad)

            Glide.with(this).load(foto).into(fotoPerfilPaciente)

            var tutor = if (it.get("Tutor legal").toString() != "null") it.get("Tutor legal")
                .toString() else ""
            textTutor.setText(tutor)


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