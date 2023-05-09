package com.myfit.view

import android.app.Dialog
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.myfit.R
import com.myfit.controladores.AppController
import com.myfit.modelo.Usuario
import com.myfit.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DialogoRegistro : DialogFragment() {
    private val model:DataViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialogo_registro,null)
        val builder= MaterialAlertDialogBuilder(requireActivity())
        isCancelable = false
        builder.setView(view)
        view.findViewById<TextView>(R.id.textoRegistro).setOnClickListener{
            val dialogo = DialogInicioSesion()
            dialogo.show(parentFragmentManager,"DialogInicioSesion")
            dismiss()
        }
        view.findViewById<Button>(R.id.botonRegistro).setOnClickListener{
            val ilEmail = view.findViewById<TextInputLayout>(R.id.campoInicioSesionMailIl)
            val ilUser =  view.findViewById<TextInputLayout>(R.id.campoNombreUsuarioIl)
            ilEmail.error = null
            ilUser.error = null
            val emailIntroducido = view.findViewById<EditText>(R.id.campoInicioSesionMail).text.toString()
            val userIntroducido = view.findViewById<EditText>(R.id.campoNombreUsuario).text.toString()
            var passIntroducida = view.findViewById<EditText>(R.id.campoInicioSesionPass).text.toString()
            passIntroducida = Utils.hashPassword(passIntroducida)
            CoroutineScope(Dispatchers.IO).launch {
                val listaUsuarios = AppController.getUsuarios()
                var isEmailRepetido = false
                var isUserRepetido = false
                if (listaUsuarios != null) {
                    listaUsuarios.forEach {
                        if(it.email == emailIntroducido ){
                            isEmailRepetido = true
                        }
                        if( it.usuario == userIntroducido){
                            isUserRepetido = true
                        }
                    }
                    if(!isEmailRepetido && !isUserRepetido){
                        val user = Usuario(0,emailIntroducido,passIntroducida,userIntroducido)
                        AppController.insertarUsuario(user)
                        Utils.estaLogeado = true
                        Utils.setUser(user)
                        withContext(Dispatchers.Main){
                            model.setTieneQueActualizarRutinas(true)
                            model.setTieneQueActualizarUser(true)

                            dismiss()
                        }
                    }else{
                        withContext(Dispatchers.Main) {
                            if(isEmailRepetido){
                                ilEmail.error = "El email introducido existe"
                            }
                            if(isUserRepetido){
                                ilUser.error = "El usuario introducido existe"
                            }
                        }
                    }
                }
            }
        }
        return builder.create()
    }
}