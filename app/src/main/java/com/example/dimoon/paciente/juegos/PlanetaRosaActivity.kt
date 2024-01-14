package com.example.dimoon.paciente.juegos

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.dimoon.R
import com.example.dimoon.paciente.BasePaciente
import com.example.dimoon.paciente.HomePacienteActivity
import com.example.dimoon.paciente.juegos.PaintView.Companion.colorList
import com.example.dimoon.paciente.juegos.PaintView.Companion.currentBrush
import com.example.dimoon.paciente.juegos.PaintView.Companion.pathList

class PlanetaRosaActivity : BasePaciente() { // Juego para dibujar formas -> Habilidad Motora
    companion object {
        var path = Path()
        var paintBrush = Paint()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planeta_rosa)

        //AÑADIR MENU
        val toolbar: Toolbar = findViewById(R.id.tooolbar_main)
        setSupportActionBar(toolbar)

        setupDrawer(toolbar)
        //----------------------------------------------------



        var redButton = findViewById<ImageButton>(R.id.redColor)
        var blueButton = findViewById<ImageButton>(R.id.blueColor)
        var greenButton = findViewById<ImageButton>(R.id.greenColor)
        var yellowButton = findViewById<ImageButton>(R.id.yellowColor)
        var blackButton = findViewById<ImageButton>(R.id.blackColor)
        var eraserButton = findViewById<ImageButton>(R.id.eraser)

        var endGame = findViewById<Button>(R.id.endGameButton)

        redButton.setOnClickListener {
            paintBrush.color = Color.RED
            currentColor(paintBrush.color)
        }

        blueButton.setOnClickListener {
            paintBrush.color = Color.BLUE
            currentColor(paintBrush.color)
        }

        greenButton.setOnClickListener {
            paintBrush.color = Color.GREEN
            currentColor(paintBrush.color)
        }

        yellowButton.setOnClickListener {
            paintBrush.color = Color.YELLOW
            currentColor(paintBrush.color)
        }

        blackButton.setOnClickListener {
            paintBrush.color = Color.BLACK
            currentColor(paintBrush.color)
        }

        eraserButton.setOnClickListener {
            pathList.clear()
            colorList.clear()
            path.reset()
        }

        endGame.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("ENHORABUENA").setMessage("¡Has hecho un dibujo precioso!")
                .setCancelable(false).setPositiveButton("PROXIMA AVENTURA",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        val intent = Intent(this, HomePacienteActivity::class.java)
                        intent.putExtra("enableButton2", true)
                        intent.putExtra("enableButton3", true)
                        intent.putExtra("enableButton4", true)
                        startActivity(intent)
                    })
            builder.show()
        }
    }
    private fun currentColor(color:Int){
        currentBrush = color
        path = Path()
    }
}