package com.myfit.view

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.myfit.R
import com.myfit.modelo.Ejercicio
import com.myfit.modelo.EjercicioRutina
import com.myfit.modelo.EjercicioRutinaPK

class DialogoEditarEjercicioRutinaCardio : DialogFragment() {
    private val model:DataViewModel by activityViewModels()
    var ejercicioRutina : EjercicioRutina? = null
    lateinit var ejercicio : Ejercicio
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialogo_editar_ejercicio_rutina_cardio,null)
        val builder= MaterialAlertDialogBuilder(requireActivity())
        builder.setView(view)
        var esEditar = this.arguments?.getBoolean("EDITAR")
        val numeroKm = view.findViewById<EditText>(R.id.numeroKilometros)
        val numeroMins = view.findViewById<EditText>(R.id.numeroMinutos)
        val updateObserver = Observer<EjercicioRutina?> {
            if(it != null){
                ejercicioRutina = it
            }

        }
        model.getEjercicioRutina.observe(requireActivity(),updateObserver)
        if (esEditar == true) {
            numeroKm.setText(ejercicioRutina?.kilometrosRecorridos.toString())
            numeroMins.setText(ejercicioRutina?.minutos.toString())
        }
        val updateObserverEjercicio = Observer<Ejercicio> {
            ejercicio = it
        }
        model.getEjercicio.observe(requireActivity(),updateObserverEjercicio)
        builder.setPositiveButton("Aceptar"
        ) { dialogo, _ ->
            if (esEditar == true) {
                ejercicioRutina!!.kilometrosRecorridos = numeroKm.text.toString().toDouble()
                ejercicioRutina!!.minutos = numeroMins.text.toString().toDouble()
                model.setEjercicioRutinaEditado(ejercicioRutina!!)
            } else {
                if(numeroMins.text.isEmpty()){
                    numeroMins.setText("0.0")
                }

                if(numeroKm.text.isEmpty()){
                    numeroKm.setText("0.0")
                }

                ejercicioRutina = EjercicioRutina(
                    0, ejercicio, EjercicioRutinaPK(ejercicio.id, 0), numeroKm.text.toString().toDouble(),
                    numeroMins.text.toString().toDouble(), 0, 0, 0
                )
                model.setEjercicioRutinaAdd(ejercicioRutina!!)
                model.setEjercicioRutinaBorrarLista(ejercicioRutina)

                Toast.makeText(requireActivity(), "Ejercicio añadido", Toast.LENGTH_SHORT).show()

            }
            dialogo.cancel()
        }
            .setNegativeButton("Cancelar") { dialogo, _ -> dialogo.dismiss() }
        return builder.create()
    }
}