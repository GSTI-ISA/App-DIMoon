package com.example.dimoon.paciente.juegos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.dimoon.R
import com.google.firebase.firestore.FirebaseFirestore

class QuizActivity : AppCompatActivity() {
    private val questionList = mutableListOf<Preguntas>()
    private var currentQuestionIndex = 0
    private val MAX_QUESTIONS = 5 // Número máximo de preguntas
    private lateinit var questionTextView: TextView
    private lateinit var btn0: Button
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionTextView = findViewById(R.id.question_textview)
        btn0 = findViewById(R.id.button0)
        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)
        btnNext = findViewById(R.id.next_question_button)

        // Inicializa tu lista de preguntas desde Firebase
        fetchQuestionsFromFirebase()

        // Muestra la primera pregunta al iniciar el test
        showNextQuestion()

        btnNext.setOnClickListener {
            showNextQuestion()
        }
    }

    private fun fetchQuestionsFromFirebase() {
        // Obtiene las preguntas desde Firebase
        FirebaseFirestore.getInstance().collection("preguntas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Toast.makeText(this, "HA LLEGADO", Toast.LENGTH_SHORT)
                    val pregunta = document.getString("pregunta") ?: ""
                    val opciones = document.get("opciones") as? List<String> ?: emptyList()
                    val respuestaCorrecta = document.getString("correcta") ?: ""

                    questionList.add(Preguntas(pregunta, opciones, respuestaCorrecta))
                }
                // Orden aleatorio
                questionList.shuffle()
            }
            .addOnFailureListener { exception ->
            }
    }

    private fun showNextQuestion() {
        if (currentQuestionIndex < questionList.size && currentQuestionIndex < MAX_QUESTIONS) {
            val currentQuestion = questionList[currentQuestionIndex]

            questionTextView.text = currentQuestion.question
            btn0.text = currentQuestion.options[0]
            btn1.text = currentQuestion.options[1]
            btn2.text = currentQuestion.options[2]
            btn3.text = currentQuestion.options[3]

            currentQuestionIndex++
        } else {
        }
    }
}