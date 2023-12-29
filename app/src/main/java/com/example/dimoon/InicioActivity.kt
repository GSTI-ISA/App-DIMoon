package com.example.dimoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class InicioActivity : AppCompatActivity() {
    private lateinit var buttonLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        buttonLogin = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener{
            intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

    }
}