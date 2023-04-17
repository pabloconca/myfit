package com.myfit.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.myfit.R
import com.myfit.utils.Utils

class DialogoEditarEjercicioRutina : DialogFragment() {
    private val model:DataViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialogo_editar_ejercicio_rutina,null)
        val builder= MaterialAlertDialogBuilder(requireActivity())
        builder.setView(view)
        var ejercicioRutina = model.getEjercicioRutina
        var numeroRep = view.findViewById<NumberPicker>(R.id.numeroRepeticiones)

        numeroRep.minValue = 0
        numeroRep.maxValue = 9999
        numeroRep.value = ejercicioRutina.value?.repeticionesEjercicio!!
        numeroRep.wrapSelectorWheel = true;


        var numeroSeries = view.findViewById<NumberPicker>(R.id.numeroSeries)
        numeroSeries.minValue = 0
        numeroSeries.maxValue = 9999
        numeroSeries.value = ejercicioRutina.value?.seriesEjercicio!!
        numeroSeries.wrapSelectorWheel = true;
        builder.setPositiveButton("Aceptar",
        DialogInterface.OnClickListener{ dialogo, id->
            ejercicioRutina.value!!.seriesEjercicio = numeroSeries.value
            ejercicioRutina.value!!.repeticionesEjercicio = numeroRep.value
            model.setEjercicioRutina(ejercicioRutina.value!!)
            dialogo.cancel()})
        .setNegativeButton("Cancelar",DialogInterface.OnClickListener{ dialogo, id->dialogo.dismiss()})

        return builder.create()
    }
}