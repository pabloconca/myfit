package com.myfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myfit.view.FragmentRutina
import com.myfit.view.FragmentUsuario

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var nav = findViewById<BottomNavigationView>(R.id.bottomNav)
        nav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.principal -> {
                    showFragment(FragmentRutina())
                    true
                }
                R.id.busqueda -> {
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