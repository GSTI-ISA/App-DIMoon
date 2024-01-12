package com.example.dimoon.paciente.juegos

import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.dimoon.R
import com.example.dimoon.medico.ShowPacienteActivity
import com.example.dimoon.paciente.BasePaciente
import com.example.dimoon.paciente.HomePacienteActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale


class PlanetaMoradoActivity : BasePaciente() {
    private  lateinit var  iv_11:ImageView
    private  lateinit var  iv_12:ImageView
    private  lateinit var  iv_13:ImageView
    private  lateinit var  iv_14:ImageView
    private  lateinit var  iv_21:ImageView
    private  lateinit var  iv_22:ImageView
    private  lateinit var  iv_23:ImageView
    private  lateinit var  iv_24:ImageView
    private  lateinit var  iv_31:ImageView
    private  lateinit var  iv_32:ImageView
    private  lateinit var  iv_33:ImageView
    private  lateinit var  iv_34:ImageView
    private lateinit var  puntuacion:TextView
    lateinit  var  mPlayer: MediaPlayer
    var  suena = false;

    var ajolote = 0
    var oso = 0
    var dinosaurio = 0
    var pollito = 0
    var foca = 0
    var oveja = 0
    var imagenesArray = arrayOf(11,12,13,14,15,16,21,22,23,24,25,26)

    var numeroImagen  = 1
    lateinit var imagen1:ImageView
    lateinit var imagen2:ImageView
    var puntos = 0

    private lateinit var cronometro:TextView
    private lateinit var cronometroContador: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planeta_morado)

        //AÑADIR MENU
        val toolbar: Toolbar = findViewById(R.id.tooolbar_main)
        setSupportActionBar(toolbar)

        setupDrawer(toolbar)
        //----------------------------------------------------


        iv_11 = findViewById(R.id.iv_11)
        iv_12 = findViewById(R.id.iv_12)
        iv_13 = findViewById(R.id.iv_13)
        iv_14 = findViewById(R.id.iv_14)
        iv_21 = findViewById(R.id.iv_21)
        iv_22 = findViewById(R.id.iv_22)
        iv_23 = findViewById(R.id.iv_23)
        iv_24 = findViewById(R.id.iv_24)
        iv_31 = findViewById(R.id.iv_31)
        iv_32 = findViewById(R.id.iv_32)
        iv_33 = findViewById(R.id.iv_33)
        iv_34 = findViewById(R.id.iv_34)

        puntuacion = findViewById(R.id.textViewPuntuacion)
        cronometro = findViewById(R.id.textViewCrono)



        sonido("background")

        //3*4 imágenes
        //agregamos a las imagenes una etiqueta para la posicion imagenes en la matriz de cartas
        iv_11.tag = "0"
        iv_12.tag = "1"
        iv_13.tag = "2"
        iv_14.tag = "3"
        iv_21.tag = "4"
        iv_22.tag = "5"
        iv_23.tag = "6"
        iv_24.tag = "7"
        iv_31.tag = "8"
        iv_32.tag = "9"
        iv_33.tag = "10"
        iv_34.tag = "11"

        ajolote = R.drawable.axolotl
        oso = R.drawable.bat
        dinosaurio = R.drawable.dinosaur
        pollito = R.drawable.chick
        foca = R.drawable.seal
        oveja = R.drawable.sheep

        //mezclar imagenes
        imagenesArray.shuffle()

        iv_11.setOnClickListener { seleccionar(it) }
        iv_12.setOnClickListener { seleccionar(it) }
        iv_13.setOnClickListener { seleccionar(it) }
        iv_14.setOnClickListener { seleccionar(it) }
        iv_21.setOnClickListener { seleccionar(it) }
        iv_22.setOnClickListener { seleccionar(it) }
        iv_23.setOnClickListener { seleccionar(it) }
        iv_24.setOnClickListener { seleccionar(it) }
        iv_31.setOnClickListener { seleccionar(it) }
        iv_32.setOnClickListener { seleccionar(it) }
        iv_33.setOnClickListener { seleccionar(it) }
        iv_34.setOnClickListener { seleccionar(it) }


        cronometro()

    }

    fun sonido(sonidoName: String,loop: Boolean=false){
        var ID = resources.getIdentifier(sonidoName,"raw", packageName)//coger id del sonido pasado por f(x)

        if( sonidoName == "background"){
            mPlayer = MediaPlayer.create(this, ID)
            mPlayer.isLooping = loop
            mPlayer.setVolume(0.03F,0.03F)
            if(!mPlayer.isPlaying) {
                mPlayer.start()
            }}
        else{
            mPlayer = MediaPlayer.create(this, ID)
            mPlayer.setOnCompletionListener(MediaPlayer.OnCompletionListener { mp ->
                mp.stop() //parar sonido
                mp.release()
            })
            if(!mPlayer.isPlaying){
                mPlayer.start()
            }

        }


    }
    fun pausarSonido(){
        if (mPlayer.isPlaying) {
            mPlayer.pause()
            suena = false
        } else {
            mPlayer.start()
            suena = true
        }
    }





    fun seleccionar (imagen: View){
        Log.d("ClickDebug", "Clicked on image: ${imagen.id}")
        //cuando selecciones carta suena touch
        sonido("touch")
        verificar(imagen)

    }

    private fun verificar(imagen: View) {
        var iv = imagen as ImageView
        //Cogeremos tag anteriores para saber hasta cual son las imagenes que tenemos y cuales las copias(parejas)
        var tag = imagen.tag.toString().toInt()//imagen a la que se hizo click--> extraemos el tag en entero

        //var imagenesArray = arrayOf(11,12,13,14,16,21,22,23,24,25,26)
        if(imagenesArray[tag]==11){//¿es la imagen en posicion 11?
            iv.setImageResource(ajolote)
        }else if (imagenesArray[tag]==12){
            iv.setImageResource((oso))
        }else if (imagenesArray[tag]==13){
            iv.setImageResource((pollito))
        }else if (imagenesArray[tag]==14){
            iv.setImageResource((dinosaurio))
        }else if (imagenesArray[tag]==15){
            iv.setImageResource((foca))
        }else if (imagenesArray[tag]==16){
            iv.setImageResource((oveja))
        }else if (imagenesArray[tag]==21){
            iv.setImageResource((ajolote))
        }else if (imagenesArray[tag]==22){
            iv.setImageResource((oso))
        }else if (imagenesArray[tag]==23){
            iv.setImageResource((pollito))
        }else if (imagenesArray[tag]==24){
            iv.setImageResource((dinosaurio))
        }else if (imagenesArray[tag]==25){
            iv.setImageResource((foca))
        }else if (imagenesArray[tag]==26){
            iv.setImageResource((oveja))
        }

        //guardar temporalmente imagen seleccionada
        if (numeroImagen==1){
            imagen1 = iv // es igual a la imagen seleccionada
            numeroImagen = 2
            iv.isEnabled = false//desabilitar imagen seleccionada para que no se seleccione la misma
        }else if (numeroImagen==2){
            imagen2 = iv // es igual a la imagen seleccionada
            numeroImagen = 1
            iv.isEnabled = false//desabilitar imagen seleccionada para que no se seleccione la misma
            deshabilitarImagenes()
            val  h = Handler(Looper.getMainLooper())
            h.postDelayed({ImagenesIguales()},1000)
        }

    }

    private fun ImagenesIguales() {
        if(imagen1.drawable.constantState == imagen2.drawable.constantState){
            sonido("success")
            puntos++//sumamos 1 punto
            puntuacion.text = "Puntuación: $puntos"

            //deshabilitamos pareja encontrada
            imagen1.isEnabled = false
            imagen2.isEnabled = false
            imagen1.tag = ""
            imagen2.tag = ""
        }else{
            sonido("no")
            //ocultamos imagen poniendo encima la carta oculta
            imagen1.setImageResource(R.drawable.oculta)
            imagen2.setImageResource(R.drawable.oculta)
        }
        //habilitar imagenes
        iv_11.isEnabled = !iv_11.tag.toString().isEmpty()//si no esta vacio habilitalo--> tag que tengan algo dentro
        iv_12.isEnabled = !iv_12.tag.toString().isEmpty()
        iv_13.isEnabled = !iv_13.tag.toString().isEmpty()
        iv_14.isEnabled = !iv_14.tag.toString().isEmpty()
        iv_21.isEnabled = !iv_21.tag.toString().isEmpty()
        iv_22.isEnabled = !iv_22.tag.toString().isEmpty()
        iv_23.isEnabled = !iv_23.tag.toString().isEmpty()
        iv_24.isEnabled = !iv_24.tag.toString().isEmpty()
        iv_31.isEnabled = !iv_31.tag.toString().isEmpty()
        iv_32.isEnabled = !iv_32.tag.toString().isEmpty()
        iv_33.isEnabled = !iv_33.tag.toString().isEmpty()
        iv_34.isEnabled = !iv_34.tag.toString().isEmpty()


    }

    private fun deshabilitarImagenes() {
        iv_11.isEnabled = false
        iv_12.isEnabled = false
        iv_13.isEnabled = false
        iv_14.isEnabled = false
        iv_21.isEnabled = false
        iv_22.isEnabled = false
        iv_23.isEnabled = false
        iv_24.isEnabled = false
        iv_31.isEnabled = false
        iv_32.isEnabled = false
        iv_33.isEnabled = false
        iv_34.isEnabled = false

    }

    fun finJuego(tiempo:String){
        if(iv_11.tag.toString().isEmpty() &&
            iv_12.tag.toString().isEmpty() &&
            iv_13.tag.toString().isEmpty() &&
            iv_14.tag.toString().isEmpty() &&
            iv_21.tag.toString().isEmpty() &&
            iv_22.tag.toString().isEmpty() &&
            iv_23.tag.toString().isEmpty() &&
            iv_24.tag.toString().isEmpty() &&
            iv_31.tag.toString().isEmpty() &&
            iv_32.tag.toString().isEmpty() &&
            iv_33.tag.toString().isEmpty() &&
            iv_34.tag.toString().isEmpty()){
            mPlayer.stop()
            mPlayer.release()
            cronometroContador.cancel()
            sonido("win")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("ENHORABUENA").setMessage("Tiempo:$tiempo").setCancelable(false).setPositiveButton("PROXIMA AVENTURA",
                DialogInterface.OnClickListener{dialogInterface,i->
                    pausarSonido()
                    val intent = Intent(this, HomePacienteActivity::class.java)
                    intent.putExtra("enableButton2", true)
                    intent.putExtra("enableButton3", true)
                    intent.putExtra("enableButton4", true)
                    startActivity(intent)
                    //val intent2 = Intent(this, ShowPacienteActivity::class.java)
                    //intent2.putExtra("tiempo_parejas", tiempo)
                    almacenarPuntuacionBD(tiempo)

                })
            builder.show()
        }
    }



    fun cronometro(){

        cronometroContador = object : CountDownTimer(600000, 1000) {//max 10min
        override fun onTick(millisUntilFinished: Long) {
            // Actualiza tu EditText con el tiempo restante
            val tiempoTranscurrido = 600000 - millisUntilFinished
            val minutos: Long = tiempoTranscurrido / 1000 / 60
            val segundos: Long = tiempoTranscurrido / 1000 % 60
            var TimeFormatted:String = String.format(Locale.getDefault(),"%02d:%02d",minutos,segundos)
            cronometro.setText(TimeFormatted)
            finJuego(TimeFormatted)
        }

            override fun onFinish() {
                val builder = AlertDialog.Builder(this@PlanetaMoradoActivity)
                builder.setTitle("SE HA ACABADO EL TIEMPO").setCancelable(false).setPositiveButton("iNTENTARLO DE NUEVO",
                    DialogInterface.OnClickListener{dialogInterface,i->
                        pausarSonido()
                        val intent = Intent(this@PlanetaMoradoActivity, PlanetaMoradoActivity::class.java)
                        startActivity(intent)

                    })
                builder.show()

            }


        }
        cronometroContador.start()
    }

    private fun almacenarPuntuacionBD(tiempo: String) {
        //accedemos a la BD
        val myBD = FirebaseFirestore.getInstance()
        //colección: "pacientes"
        val myCol = myBD.collection("Pacientes")



        //paciente
        var user = Firebase.auth.currentUser?.email.toString()


        val myDoc = myCol.document(user).update("tiempoParejas", tiempo)
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

