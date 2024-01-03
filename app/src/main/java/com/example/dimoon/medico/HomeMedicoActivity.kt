package com.example.dimoon.medico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dimoon.Paciente
import com.example.dimoon.R
import com.example.dimoon.medico.adapter.PacientesAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class HomeMedicoActivity : AppCompatActivity() {
    private lateinit var listadoPac: RecyclerView
    private lateinit var email: String
    private lateinit var pacientesList: ArrayList<Paciente>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_medico)

        listadoPac = findViewById(R.id.RecyclerPacientes)
        email = intent.getStringExtra("user") ?: ""//email medico

        pacientesList = ArrayList()
        muestraNombreMedico()

    }

    fun muestraNombreMedico() {
        val myBD = FirebaseFirestore.getInstance()
        val medicosCollection = myBD.collection("Medicos").document(email)

        medicosCollection.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val nombreMedico: String = documentSnapshot.getString("nombre") ?: ""
                    Log.d("Firestore", "NombreMedico: $nombreMedico");

                    compararNombreMedico(nombreMedico)

                } else {
                }
            }
            .addOnFailureListener { exception ->
                println("Error al leer el documento del médico: $exception")
            }
    }

    fun compararNombreMedico(nombreMedico: String) {
        //accede BD
        val myBD = FirebaseFirestore.getInstance()
        val pacientesCollection = myBD.collection("Pacientes")

        pacientesCollection.whereEqualTo("medico",nombreMedico).get()
            .addOnSuccessListener { result -> pacientesList

                for (document in result) {
                    val pacienteMedico = document.getString("medico")
                    //si el nombre del medico es el mismo que el del medico del paciente
                    // Crea un objeto Paciente y agrégalo a la lista
                    val nombrePaciente = document.getString("nombre") ?: ""
                    val enfermedadPaciente = document.getString("enfermedad") ?: ""
                    val fotoPaciente = document.getString("foto") ?: ""


                    val paciente = Paciente(nombrePaciente, enfermedadPaciente,fotoPaciente)
                    pacientesList.add(paciente)
                    Log.d("Firestore", "Paciente List: $pacientesList")
                    initRecyclerView(pacientesList)
                    }
                }


            }



    fun initRecyclerView(pacientesList:List<Paciente>){
        Log.d("Firestore", "Paciente List: $pacientesList")
        //recuperar recyclerView
        listadoPac.layoutManager = LinearLayoutManager(this)

        listadoPac.adapter = PacientesAdapter(pacientesList)
    }




}