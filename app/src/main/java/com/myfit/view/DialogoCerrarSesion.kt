package com.myfit.view

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.myfit.utils.Utils

class DialogoCerrarSesion : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder=MaterialAlertDialogBuilder(requireActivity())
        builder.setMessage("¿Seguro que deseas cerrar sesión?")
            .setTitle("Cerrar sesión")
            .setPositiveButton("Si"
            ) { dialogo, _ ->
                Utils.estaLogeado = false
                Utils.mostrarDialogoInicioSesion(parentFragmentManager)
                dialogo.cancel()
            }
            .setNegativeButton("No") { dialogo, _ -> dialogo.dismiss() }
        return builder.create()
    }

}