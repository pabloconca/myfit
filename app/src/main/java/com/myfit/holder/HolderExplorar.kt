package com.myfit.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.modelo.Rutina

class HolderExplorar(v : View) : RecyclerView.ViewHolder(v), View.OnClickListener {
    var nombre: TextView
    var propietario: TextView
    var tipo: TextView
    var cantidadEjercicios: TextView
    init {
        nombre = v.findViewById(R.id.nombre)
        propietario = v.findViewById(R.id.usuario)
        cantidadEjercicios = v.findViewById(R.id.count)
        tipo = v.findViewById(R.id.tipo)
    }
    fun bind(rutina: Rutina?){
        if (rutina != null) {
            nombre.text = rutina.nombre
            propietario.text = "Propietario: "+rutina.idUsuario.usuario
            cantidadEjercicios.text = "Ejercicios: "+rutina.ejercicioRutinaCollection.count().toString()
            tipo.text = "Tipo: "+getTipos(rutina)
        }
    }

    private fun getTipos(rutina: Rutina?): String {
        var result = ""
        rutina?.ejercicioRutinaCollection?.forEach {
            if (!result.contains(it.ejercicio.tipo)) {
                result += "${it.ejercicio.tipo}, "
            }
        }
        if (result.endsWith(", ")) {
            result = result.substring(0, result.length - 2)
        }
        return result
    }
    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}