package com.example.dimoon.paciente.juegos

import android.os.Parcelable
import java.io.Serializable

data class Pregunta(
    val question: String,
    val options: List<String>,
    val correct: String,
)