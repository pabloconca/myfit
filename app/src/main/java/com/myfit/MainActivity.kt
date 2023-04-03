package com.myfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myfit.controladores.AppController
import com.myfit.view.FragmentExplorar
import com.myfit.view.FragmentRutina
import com.myfit.view.FragmentUsuario

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppController.inicializarRetrofit()
        var nav = findViewById<BottomNavigationView>(R.id.bottomNav)
        nav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.principal -> {
                    showFragment(FragmentRutina())
                    true
                }
                R.id.busqueda -> {
                    showFragment(FragmentExplorar())
                    true
                }
                R.id.settings -> {
                    showFragment(FragmentUsuario())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor, fragment)
            .commit()
    }

}