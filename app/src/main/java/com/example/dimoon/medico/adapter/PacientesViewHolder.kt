package com.example.dimoon.medico.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dimoon.Paciente
import com.example.dimoon.R
import org.w3c.dom.Text

class PacientesViewHolder(view: View): RecyclerView.ViewHolder(view){

    val paciente = view.findViewById<TextView>(R.id.tvPacienteNombre)
    val enfermedad = view.findViewById<TextView>(R.id.tvPacienteEnfermedad)
    val foto = view.findViewById<ImageView>(R.id.ivPaciente)

    fun render(pacienteModel: Paciente){
        paciente.text = pacienteModel.nombre
        enfermedad.text = pacienteModel.enfermedad
        Glide.with(foto.context).load(pacienteModel.foto).into(foto)



    }

}