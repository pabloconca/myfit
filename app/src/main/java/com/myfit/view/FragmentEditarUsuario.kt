package com.myfit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.textfield.TextInputEditText
import com.myfit.R
import com.myfit.controladores.AppController
import com.myfit.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentEditarUsuario : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar_usuario,container,false)
        view.findViewById<EditText>(R.id.campoNuevoNombre).setText(Utils.usuarioActual.usuario)
        view.findViewById<EditText>(R.id.campoNuevoEmail).setText(Utils.usuarioActual.email)
        view.findViewById<Button>(R.id.botonAceptar).setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                Utils.usuarioActual.usuario = view.findViewById<EditText>(R.id.campoNuevoNombre).text.toString()
                Utils.usuarioActual.email = view.findViewById<EditText>(R.id.campoNuevoEmail).text.toString()
                AppController.updateUsuario(Utils.usuarioActual)
                withContext(Dispatchers.Main){
                    volverAVistaUsuario()
                }
            }
        }
        view.findViewById<Button>(R.id.botonCancelar).setOnClickListener{
            volverAVistaUsuario()
        }
        return view
    }
    private fun volverAVistaUsuario(){
        val navController= NavHostFragment.findNavController(this@FragmentEditarUsuario)
        if (navController.currentDestination?.id == R.id.fragmentEditarUsuario)
            navController.navigate(R.id.action_fragmentEditarUsuario_to_fragmentUsuario)
    }
}