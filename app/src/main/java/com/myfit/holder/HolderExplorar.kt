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
    var cantidadEjercicios: TextView
    init {
        nombre = v.findViewById(R.id.nombre)
        propietario = v.findViewById(R.id.usuario)
        cantidadEjercicios = v.findViewById(R.id.count)
    }
    fun bind(rutina: Rutina?){
        if (rutina != null) {
            nombre.text = rutina.nombre
            propietario.text = rutina.idUsuario.usuario
            cantidadEjercicios.text = rutina.ejercicioRutinaCollection.count().toString()
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}