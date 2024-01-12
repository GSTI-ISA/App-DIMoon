package com.example.dimoon.paciente.juegos

import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dimoon.Paciente
import com.example.dimoon.R
import com.example.dimoon.medico.ShowPacienteActivity
import com.example.dimoon.medico.adapter.PacienteClickListener
import com.example.dimoon.medico.adapter.PacientesAdapter
import com.example.dimoon.paciente.HomePacienteActivity
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

class QuizActivity : AppCompatActivity(), View.OnClickListener {
    private val questionList = mutableListOf<Pregunta>()
    var currentQuestionIndex = 0
    var selectedAnswer = ""
    var score = 0
    var preguntasRespondidas = 0


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


        btn0.setOnClickListener(this)
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)





        fetchQuestionsFromFirebase()


    }


    private fun fetchQuestionsFromFirebase() {

        selectedAnswer = ""

        // Obtiene las preguntas desde Firebase
        FirebaseFirestore.getInstance().collection("Preguntas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val pregunta = document.getString("Pregunta") ?: ""
                    val opciones = document.get("Opciones") as? List<String> ?: emptyList()
                    val respuestaCorrecta = document.getString("Correcta") ?: ""

                    questionList.add(Pregunta(pregunta, opciones, respuestaCorrecta))



                }
                // Muestra la primera pregunta al iniciar el test

                questionList.shuffle()
                showNextQuestion(btnNext)
            }
            .addOnFailureListener { exception ->
            }

    }




    private fun showNextQuestion(button: Button) {
        button.setBackgroundColor(getColor(R.color.white))

        if (currentQuestionIndex < questionList.size && currentQuestionIndex < MAX_QUESTIONS) {
            val currentQuestion = questionList[currentQuestionIndex]

            questionTextView.text = currentQuestion.question
            btn0.text = currentQuestion.options[0]
            btn1.text = currentQuestion.options[1]
            btn2.text = currentQuestion.options[2]
            btn3.text = currentQuestion.options[3]


            preguntasRespondidas++


        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        val totalQuestions = questionList.size
        val percentage = ((score.toFloat() / MAX_QUESTIONS.toFloat() * 100)).toInt()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ENHORABUENA").setMessage("Preguntas correctas:$score")
            .setMessage("Porcentaje obtenido: $percentage").setCancelable(false)
            .setPositiveButton("PROXIMA AVENTURA",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    val intent = Intent(this, HomePacienteActivity::class.java)
                    intent.putExtra("enableButton2", true)
                    intent.putExtra("enableButton3", true)
                    startActivity(intent)
                    almacenarPuntuacionBD(percentage)


                })
        builder.show()


    }

    override fun onClick(view: View) {

        btn0.setBackgroundColor(getColor(R.color.white))
        btn3.setBackgroundColor(getColor(R.color.white))
        btn2.setBackgroundColor(getColor(R.color.white))
        btn1.setBackgroundColor(getColor(R.color.white))

        val clickedButton = view as Button
        selectedAnswer = clickedButton.text.toString()

        clickedButton.setBackgroundColor(getColor(R.color.primary))
        btnNext.setOnClickListener {
            if (selectedAnswer == questionList[currentQuestionIndex].correct) {
                clickedButton.setBackgroundColor(getColor(R.color.pregunta_correcta))
                score++
            } else {
                clickedButton.setBackgroundColor(getColor(R.color.pregunta_incorrecta))

            }
            currentQuestionIndex++
            Handler(Looper.getMainLooper()).postDelayed({
                showNextQuestion(clickedButton)
                                }, 1000)
            //Toast.makeText(this, "$preguntasRespondidas", Toast.LENGTH_SHORT).show()

        }


    }


    private fun almacenarPuntuacionBD(puntuacion: Int) {
        //accedemos a la BD
        val myBD = FirebaseFirestore.getInstance()
        //colección: "pacientes"
        val myCol = myBD.collection("Pacientes")



        //paciente
        var user = Firebase.auth.currentUser?.email.toString()


        val myDoc = myCol.document(user).update("puntuacionQuiz", puntuacion)
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

}
