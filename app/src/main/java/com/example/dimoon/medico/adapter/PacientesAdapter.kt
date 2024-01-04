package com.example.dimoon.medico.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat.setBackground
import androidx.recyclerview.widget.RecyclerView
import com.example.dimoon.Paciente
import com.example.dimoon.R

interface PacienteClickListener {
    fun onPacienteClick(paciente:Paciente)
}
class PacientesAdapter(private val pacientesList:List<Paciente>, private val pacienteClickListener:PacienteClickListener): RecyclerView.Adapter<PacientesViewHolder>() {

    private lateinit var  listener:View.OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacientesViewHolder {//devuelve al ViewHolder para  coger atributos y mostrarlos

        //devuelve item layout por cada paciente de la lista ViewHolder
        val LayoutInflater = LayoutInflater.from(parent.context)//contexto de cualquiera de lass vistas-->ViewGroup
        return PacientesViewHolder(LayoutInflater.inflate(R.layout.item_paciente, parent, false))//recibe vista

    }

    override fun getItemCount(): Int = pacientesList.size//tamño lista

    override fun onBindViewHolder(holder: PacientesViewHolder, position: Int) {
        // pasa por cada item y va a llamar a PacienteRender

        val paciente = pacientesList[position]//posicion a la que acceder
        holder.render(paciente)//Lamar a la funcion pasándole el item
        holder.itemView.setOnClickListener{pacienteClickListener.onPacienteClick(paciente)}
    }





}