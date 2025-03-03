package com.example.dimoon.paciente

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.dimoon.AuthActivity
import com.example.dimoon.R
import com.example.dimoon.medico.HomeMedicoActivity
import com.example.dimoon.medico.PerfilMedicoActivity
import com.example.dimoon.paciente.juegos.PlanetaMoradoActivity
import com.example.dimoon.paciente.juegos.PlanetaRosaActivity
import com.example.dimoon.paciente.juegos.PlanetaVerdeActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

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
        val user = Firebase.auth.currentUser // Acceder al usuario autenticado ahora mismo
        val email = user?.email.toString()


        if (id == R.id.nav_item_one) {
            val intent = Intent(this, HomePacienteActivity::class.java)

            Toast.makeText(this, "Paciente seleccionado ${email}", Toast.LENGTH_SHORT).show()

            if (email != null) {
                val db = FirebaseFirestore.getInstance()
                val pacienteCol = db.collection("Pacientes")
                val medicoCol = db.collection("Medicos")

                pacienteCol.document(email).get().addOnSuccessListener {
                    if (it.exists()) {
                        if (this is PlanetaMoradoActivity) {
                            intent.putExtra("enableButton2", true)
                            intent.putExtra("enableButton3", true)
                            startActivity(intent)
                        } else if (this is PlanetaRosaActivity) {
                            intent.putExtra("enableButton2", true)
                            intent.putExtra("enableButton3", true)
                            intent.putExtra("enableButton4", true)
                            startActivity(intent)
                        } else if (this is PlanetaVerdeActivity) {
                            intent.putExtra("enableButton2", true)
                            startActivity(intent)
                        } else {
                            startActivity(intent)
                        }
                    } else {
                        medicoCol.document(email).get().addOnSuccessListener { medicoDocument ->
                            if (medicoDocument.exists()) {
                                val intent = Intent(this, HomeMedicoActivity::class.java)
                                intent.putExtra("user", email)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        } else if (id == R.id.nav_item_four) {
            if (email != null) {
                val db = FirebaseFirestore.getInstance()
                val pacienteCol = db.collection("Pacientes")
                val medicoCol = db.collection("Medicos")

                pacienteCol.document(email).get().addOnSuccessListener {
                    if (it.exists()) {
                        startActivity(Intent(this, PerfilPacienteActivity::class.java))

                    } else {
                        medicoCol.document(email).get().addOnSuccessListener { medicoDocument ->
                            if (medicoDocument.exists()) {
                                startActivity(Intent(this, PerfilMedicoActivity::class.java))

                            }
                        }
                    }
                }
            }
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