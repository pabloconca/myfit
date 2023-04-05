package com.myfit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.textfield.TextInputEditText
import com.myfit.R
import com.myfit.controladores.AppController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest

class FragmentEditarPassword : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_editar_password,container,false)
        view.findViewById<Button>(R.id.cambiarPass).setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                var hashPassAnterior = ""//TODO hay que conseguir el hash actual
                var nuevaPassword = view.findViewById<TextInputEditText>(R.id.campoPassNueva).text.toString()
                nuevaPassword = hashPassword(nuevaPassword)
                if(compararPass(hashPassAnterior, nuevaPassword)){
                    //crear el usuario con los datos nuevos
                    //AppController.updateUsuario()
                    // si sale bien volver a la pagina anterior
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
    private fun compararPass(hashAnterior : String, hashNuevo : String) : Boolean{
        return hashAnterior == hashNuevo
    }
    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = password.toByteArray(Charsets.UTF_8)
        val hashedBytes = digest.digest(bytes)
        return hashedBytes.joinToString("") {
            "%02x".format(it)
        }
    }}