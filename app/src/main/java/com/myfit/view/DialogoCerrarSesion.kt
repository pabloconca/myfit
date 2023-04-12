package com.myfit.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.myfit.utils.Utils

class DialogoCerrarSesion : DialogFragment() {
    private val model:DataViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder=MaterialAlertDialogBuilder(requireActivity())
        builder.setMessage("¿Seguro que deseas cerrar sesión?")
            .setTitle("Cerrar sesión")
            .setPositiveButton("Si",
                DialogInterface.OnClickListener{ dialogo, id->
                    Utils.estaLogeado = false
                    Utils.mostrarDialogoInicioSesion(parentFragmentManager)
                    dialogo.cancel()})
            .setNegativeButton("No",DialogInterface.OnClickListener{ dialogo, id->dialogo.dismiss()})
        return builder.create()
    }

}