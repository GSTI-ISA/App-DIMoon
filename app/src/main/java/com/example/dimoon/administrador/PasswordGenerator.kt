package com.example.dimoon.administrador

import android.health.connect.datatypes.units.Length

class PasswordGenerator {

    private val  characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray()

    fun generatePassword(length: Int, specialWord: String = ""):String{
        val sb  = StringBuilder(length)

        for (x in 0 until length){
            val random = (characters.indices).random()
            sb.append(characters[random])
        }
        sb.insert((0 until length).random(), specialWord)
        return sb.toString()
    }
}