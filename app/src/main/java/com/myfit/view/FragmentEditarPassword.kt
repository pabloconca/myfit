package com.myfit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.myfit.R
import com.myfit.controladores.AppController
import com.myfit.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentEditarPassword : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_editar_password,container,false)
        view.findViewById<Button>(R.id.cambiarPass).setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val til = view.findViewById<TextInputLayout>(R.id.contAntiguaTil)
                val usuario = Utils.getUser()
                val hashPassAnterior = usuario.password
                var antiguaPass = view.findViewById<TextInputEditText>(R.id.campoPassAntigua).text.toString()
                val nuevaPass = view.findViewById<TextInputEditText>(R.id.campoPassNueva).text.toString()
                antiguaPass = Utils.hashPassword(antiguaPass)
                if(Utils.compararPass(hashPassAnterior, antiguaPass)){
                    usuario.password = Utils.hashPassword(nuevaPass)
                    AppController.updateUsuario(usuario)
                    withContext(Dispatchers.Main){
                        val navController= NavHostFragment.findNavController(this@FragmentEditarPassword)
                        if (navController.currentDestination?.id == R.id.fragmentEditarPassword)
                            navController.navigate(R.id.action_fragmentEditarPassword_to_fragmentUsuario)
                    }
                }else{
                    withContext(Dispatchers.Main){
                        til.isErrorEnabled = true
                        til.error = "La contraseña introducida no coincide"
                    }

                }

            }

        }

        view.findViewById<Button>(R.id.cancelarCambiarPass).setOnClickListener{
            val navController= NavHostFragment.findNavController(this)
            if (navController.currentDestination?.id == R.id.fragmentEditarPassword)
                navController.navigate(R.id.action_fragmentEditarPassword_to_fragmentUsuario)
            }

        return view
    }

}