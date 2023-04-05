package com.myfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myfit.controladores.AppController
import com.myfit.view.FragmentExplorar
import com.myfit.view.FragmentRutina
import com.myfit.view.FragmentUsuario

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppController.inicializarRetrofit()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.contenedor) as NavHostFragment
        navController = navHostFragment.navController
        val navView: BottomNavigationView = findViewById(R.id.bottomNav)
        navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.principal -> {
                    navController.navigate(R.id.fragmentRutina)
                    true
                }
                R.id.busqueda -> {
                    navController.navigate(R.id.fragmentExplorar)
                    true
                }
                R.id.settings -> {
                    navController.navigate(R.id.fragmentUsuario)
                    true
                }
                else -> false
            }
        }
    }
}


