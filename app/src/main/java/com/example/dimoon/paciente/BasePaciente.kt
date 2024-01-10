package com.example.dimoon.paciente

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.dimoon.AuthActivity
import com.example.dimoon.R
import com.example.dimoon.paciente.juegos.PlanetaMoradoActivity
import com.example.dimoon.paciente.juegos.PlanetaRojoActivity
import com.example.dimoon.paciente.juegos.PlanetaRosaActivity
import com.example.dimoon.paciente.juegos.PlanetaVerdeActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

open class BasePaciente : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout//diseño navegación
    private lateinit var toogle: ActionBarDrawerToggle// para conectar navegacion-ActionBaroverride fun onCreate(savedInstanceState: Bundle?) {

    protected fun setupDrawer(toolbar: Toolbar) {

        drawer = findViewById(R.id.drawer_layout)//cojon de navegacion


        // toogle-> a conectar ActionBar-Cajon de Navegacion
        toogle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toogle)//menu icono Toolbar para que salga el Cajón de Navegación
        toogle.syncState()

        //Vista en Cajón navegacion
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)//ir a home desde otro
        supportActionBar?.setHomeButtonEnabled(true)// boton para brir y cerrar el cajon de navegacion


    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId

        if (id == R.id.nav_item_one) {
            startActivity(Intent(this, HomePacienteActivity::class.java))
        } else if (id == R.id.nav_item_four) {
            startActivity(Intent(this, PerfilPacienteActivity::class.java))
        } else if (id == R.id.nav_item_five) {
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