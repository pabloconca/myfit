package com.myfit.view

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.myfit.controladores.AppController
import com.myfit.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogoEliminarUsuario : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder=MaterialAlertDialogBuilder(requireActivity())
        builder.setMessage("¿Seguro que deseas eliminar tu cuenta?")
            .setTitle("Eliminar cuenta")
            .setPositiveButton("Si"
            ) { dialogo, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    AppController.deleteUsuario(Utils.usuarioActual.id)
                }
                Utils.estaLogeado = false
                Utils.mostrarDialogoInicioSesion(parentFragmentManager)
                dialogo.cancel()
            }
            .setNegativeButton("No") { dialogo, _ -> dialogo.dismiss() }
        return builder.create()
    }
}