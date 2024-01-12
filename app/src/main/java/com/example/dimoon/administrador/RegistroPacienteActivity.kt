package com.example.dimoon.administrador

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.dimoon.R
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat


class RegistroPacienteActivity : AppCompatActivity() {
    private lateinit var spinnerEnfermedad: Spinner
    private lateinit var fechaNac: EditText
    private lateinit var nombrePacR: EditText
    private lateinit var apellidosPacR: EditText
    private lateinit var tutorPac: EditText
    private lateinit var buttonRegistro: Button
    private lateinit var buttonCancelarRegistro: Button
    private lateinit var email: String
    private lateinit var spinnerMedicos: Spinner
    private lateinit var foto: EditText
    private lateinit var buttonFoto: Button

    private  val GALLERY_INTENT = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_paciente)

        spinnerEnfermedad = findViewById(R.id.spinnerEnfermedad)
        fechaNac = findViewById(R.id.editTextFechaNacR)
        nombrePacR = findViewById(R.id.editTextNombreR)
        apellidosPacR = findViewById(R.id.editTextApellidosR)
        tutorPac = findViewById(R.id.editTextTutor)
        foto = findViewById(R.id.editTextFto)
        buttonFoto = findViewById(R.id.buttonFoto)

        var mStorage = FirebaseStorage.getInstance().reference
        buttonFoto.setOnClickListener{
            subirFoto()

        }



       // crearAdaptador()
        medicos()
        enfermedades()

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
                apellidosPacR.text.clear()
                fechaNac.text.clear()
                tutorPac.text.clear()
            }
        } else {
            showAlert("El usuario no se ha registrado correctamente")
        }

        spinnerMedicos = findViewById(R.id.spinnerMedico)

    }

    /*private fun crearAdaptador() {
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEnfermedad.adapter = adaptador
    }*/

    fun subirFoto(){
        intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent, GALLERY_INTENT)

    }

   /* fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent){
        super.onActivityResult(requestCode,resultCode,data)
        //comproba que tenemos la foto subida
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK)
            //guardamos foto de galeria
            val uri: Uri? = data?.data
            val filePath: StorageReference = mStorage.child(uri.lastPathSegment)

        filePath.putFile(uri).addOnSuccessListener {

        }

    }*/

    private fun almacenarInfoBD() {
        //accedemos a la BD
        val myBD = FirebaseFirestore.getInstance()
        //colección: "pacientes"
        val myCol = myBD.collection("Pacientes")

        //convertir de string a Timestamp
        var formato = SimpleDateFormat("dd/MM/yyyy")
        var fecha = formato.parse(fechaNac.text.toString()) //Date
        var timestamp = Timestamp(fecha)


        var nuevaInfo = mapOf(
            "nombre" to nombrePacR.text.toString(),
            "apellidos" to apellidosPacR.text.toString(),
            "fecha de nacimiento" to timestamp,
            "enfermedad" to spinnerEnfermedad.selectedItem.toString(),
            "Tutor legal" to tutorPac.text.toString(),
            "medico" to spinnerMedicos.selectedItem.toString(),
            "foto" to foto.text.toString()
        )
        val myDoc = myCol.document(email).set(nuevaInfo)
            .addOnSuccessListener {
                showAlert("Datos actualizados")
            }
            .addOnFailureListener { e ->
                showAlert("Error")
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


    private fun medicos() {
        val db = FirebaseFirestore.getInstance()
        val medicosCollection = db.collection("Medicos")
        medicosCollection.get()
            .addOnSuccessListener { result ->   val medicosList = mutableListOf<String>()
                medicosList.add("Selecciona un médico")//hint

                for (document in result) {

                    val medicoNombre = document.getString("nombre")
                    if (medicoNombre != null) {
                        medicosList.add(medicoNombre)
                    }
                }
                Log.d("Firestore", "Medicos List: $medicosList")


                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, medicosList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerMedicos.adapter = adapter
            }
            .addOnFailureListener { exception ->
                // Handle failures
                showAlert("Error fetching data from Firestore: $exception")
            }
    }
    private fun enfermedades() {
        val db = FirebaseFirestore.getInstance()
        val enfermedadesCollection = db.collection("Enfermedades")
        enfermedadesCollection.get()
            .addOnSuccessListener { result ->   val enfermedadesList = mutableListOf<String>()
                enfermedadesList.add("Enfermedad que padece")//hint


                for (document in result) {
                    val enfermedadNombre = document.getString("nombre")
                    if (enfermedadNombre != null) {
                        enfermedadesList.add(enfermedadNombre)
                    }
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, enfermedadesList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerEnfermedad.adapter = adapter
            }
            .addOnFailureListener { exception ->
                showAlert("Error fetching data from Firestore: $exception")
            }
    }

}