package com.myfit.view

import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.myfit.R
import com.myfit.modelo.Ejercicio
import com.myfit.modelo.EjercicioRutina
import com.myfit.modelo.EjercicioRutinaPK

class DialogoEditarEjercicioRutina : DialogFragment() {
    private val model:DataViewModel by activityViewModels()
    var ejercicioRutina : EjercicioRutina? = null
    lateinit var ejercicio : Ejercicio
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialogo_editar_ejercicio_rutina,null)
        val builder= MaterialAlertDialogBuilder(requireActivity())
        builder.setView(view)

        val numeroRep = view.findViewById<NumberPicker>(R.id.numeroRepeticiones)

        numeroRep.minValue = 0
        numeroRep.maxValue = 200
        numeroRep.wrapSelectorWheel = true

        val numeroSeries = view.findViewById<NumberPicker>(R.id.numeroSeries)
        numeroSeries.minValue = 0
        numeroSeries.maxValue = 200
        numeroSeries.wrapSelectorWheel = true
        val updateObserver = Observer<EjercicioRutina?> {
            if(it != null){
                ejercicioRutina = it
                numeroRep.value = it.repeticionesEjercicio
                numeroSeries.value = it.seriesEjercicio
                model.setEjercicioRutina(null)
            }

        }
        model.getEjercicioRutina.observe(requireActivity(),updateObserver)

        val updateObserverEjercicio = Observer<Ejercicio> {
            ejercicio = it
        }
        model.getEjercicio.observe(requireActivity(),updateObserverEjercicio)
        builder.setPositiveButton("Aceptar"
        ) { dialogo, _ ->
            if (ejercicioRutina != null) {
                ejercicioRutina!!.seriesEjercicio = numeroSeries.value
                ejercicioRutina!!.repeticionesEjercicio = numeroRep.value
                model.setEjercicioRutinaEditado(ejercicioRutina!!)
            } else {
                ejercicioRutina = EjercicioRutina(
                    0, ejercicio, EjercicioRutinaPK(ejercicio.id, 0), 0.0,
                    0.0, numeroRep.value, numeroSeries.value, 0
                )
                model.setEjercicioRutinaAdd(ejercicioRutina)
                model.setEjercicioRutinaBorrarLista(ejercicioRutina)
                Toast.makeText(requireActivity(), "Ejercicio añadido", Toast.LENGTH_SHORT).show()

            }
            dialogo.cancel()
        }
            .setNegativeButton("Cancelar") { dialogo, _ -> dialogo.dismiss() }

        return builder.create()
    }
}