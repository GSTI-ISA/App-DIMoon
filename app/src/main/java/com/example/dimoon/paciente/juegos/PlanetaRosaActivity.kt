package com.example.dimoon.paciente.juegos

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.example.dimoon.R
import com.example.dimoon.paciente.juegos.PaintView.Companion.colorList
import com.example.dimoon.paciente.juegos.PaintView.Companion.currentBrush
import com.example.dimoon.paciente.juegos.PaintView.Companion.pathList

class PlanetaRosaActivity : AppCompatActivity() { // Juego para dibujar formas -> Habilidad Motora
    companion object {
        var path = Path()
        var paintBrush = Paint()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planeta_rosa)

        var redButton = findViewById<ImageButton>(R.id.redColor)
        var blueButton = findViewById<ImageButton>(R.id.blueColor)
        var greenButton = findViewById<ImageButton>(R.id.greenColor)
        var yellowButton = findViewById<ImageButton>(R.id.yellowColor)
        var blackButton = findViewById<ImageButton>(R.id.blackColor)
        var eraserButton = findViewById<ImageButton>(R.id.eraser)

        redButton.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            paintBrush.color = Color.RED
            currentColor(paintBrush.color)
        }

        blueButton.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            paintBrush.color = Color.BLUE
            currentColor(paintBrush.color)
        }

        greenButton.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            paintBrush.color = Color.GREEN
            currentColor(paintBrush.color)
        }

        yellowButton.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            paintBrush.color = Color.YELLOW
            currentColor(paintBrush.color)
        }

        blackButton.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            paintBrush.color = Color.BLACK
            currentColor(paintBrush.color)
        }

        eraserButton.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            pathList.clear()
            colorList.clear()
            path.reset()
        }
    }

    private fun currentColor(color:Int){
        currentBrush = color
        path = Path()
    }
}