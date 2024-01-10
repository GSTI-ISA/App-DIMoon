package com.example.dimoon.paciente

import android.animation.ObjectAnimator
import android.app.ProgressDialog.show
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.dimoon.AuthActivity
import com.example.dimoon.R
import com.example.dimoon.paciente.juegos.PlanetaMoradoActivity
import com.example.dimoon.paciente.juegos.PlanetaRojoActivity
import com.example.dimoon.paciente.juegos.PlanetaRosaActivity
import com.example.dimoon.paciente.juegos.PlanetaVerdeActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class HomePacienteActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout//diseño navegación
    private lateinit var toogle: ActionBarDrawerToggle// para conectar navegacion-ActionBar

    //private lateinit var btnPerfilPaciente:Button
    private lateinit var btnPlanet1: ImageButton
    private lateinit var btnPlanet2: ImageButton
    private lateinit var btnPlanet3: ImageButton
    private lateinit var btnPlanet4: ImageButton
    private lateinit var imageDidi: ImageView

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_paciente)

        var toolbar: Toolbar = findViewById(R.id.tooolbar_main)//
        setSupportActionBar(toolbar)// toolbar(vista personalizada)-->ActionBar

        drawer = findViewById(R.id.drawer_layout)//cojon de navegacion


        // toogle-> a conectar ActionBar-Cajon de Navegacion
        toogle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.setDrawerListener(toogle)//menu icono Toolbar para que salga el Cajón de Navegación
        toogle.syncState()

        //Vista en Cajón navegacion
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)//ir a home desde otro
        supportActionBar?.setHomeButtonEnabled(true)// boton para brir y cerrar el cajon de navegacion


       // email = intent.getStringExtra("user") ?: ""//email paciente

        //btnPerfilPaciente = findViewById(R.id.btnPerfil)
        btnPlanet1 = findViewById(R.id.btnPlanet1)
        btnPlanet2 = findViewById(R.id.btnPlanet2)
        btnPlanet3 = findViewById(R.id.btnPlanet3)
        btnPlanet4 = findViewById(R.id.btnPlanet4)
        imageDidi = findViewById(R.id.imageViewDidi)



        btnPlanet1.setOnClickListener {
            intent = Intent(this, PlanetaRojoActivity::class.java)
            startActivity(intent)
        }

        btnPlanet2.setOnClickListener {
            intent = Intent(this, PlanetaVerdeActivity::class.java)
            startActivity(intent)
        }

        btnPlanet3.setOnClickListener {
            intent = Intent(this, PlanetaMoradoActivity::class.java)
            startActivity(intent)
        }

        btnPlanet4.setOnClickListener {
            intent = Intent(this, PlanetaRosaActivity::class.java)
            startActivity(intent)
        }


    }





    override fun onNavigationItemSelected(item: MenuItem): Boolean{
       var id:Int = item.itemId

        if(id==R.id.nav_item_one){
            startActivity(Intent(this, HomePacienteActivity::class.java))
        }else if(id== R.id.nav_item_four){
            startActivity(Intent(this, PerfilPacienteActivity::class.java))
        }else if(id==R.id.nav_item_five){
            Firebase.auth.signOut()
            startActivity(Intent(this, AuthActivity::class.java))
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toogle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toogle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}


