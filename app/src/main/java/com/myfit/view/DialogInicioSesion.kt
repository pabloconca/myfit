package com.myfit.view

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.myfit.R
import com.myfit.controladores.AppController
import com.myfit.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.Util

class DialogInicioSesion : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialogo_inicio,null)
        isCancelable = false
        val builder= MaterialAlertDialogBuilder(requireActivity())
        builder.setView(view)
        view.findViewById<Button>(R.id.botonIniciarSesion).setOnClickListener{
            var ilEmail = view.findViewById<TextInputLayout>(R.id.campoInicioSesionMailIl)
            var ilPass =  view.findViewById<TextInputLayout>(R.id.campoInicioSesionPassIl)
            var emailIntroducido = view.findViewById<EditText>(R.id.campoInicioSesionMail).text.toString()
            var passIntroducida = view.findViewById<EditText>(R.id.campoInicioSesionPass).text.toString()
            passIntroducida = Utils.hashPassword(passIntroducida)
            CoroutineScope(Dispatchers.IO).launch {
                var usuarios = AppController.getUsuarios()
                usuarios?.forEach {
                    if (it.email == emailIntroducido && it.password == passIntroducida){
                        Utils.setUser(it)
                        Utils.estaLogeado = true
                        dismiss()
                    }
                }
                withContext(Dispatchers.Main){
                    ilEmail.error = "La contraseña introducida no coincide"
                    ilPass.error = "La contraseña introducida no coincide"

                }
            }
        }

        return builder.create()
    }
}