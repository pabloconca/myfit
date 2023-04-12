package com.myfit.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.modelo.EjercicioRutina
import com.myfit.modelo.Rutina

class AdaptadorEjercicios(private val ejercicios: List<EjercicioRutina>) : RecyclerView.Adapter<AdaptadorEjercicios.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.linea_ejercicio, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ejercicio = ejercicios[position]
        holder.bind(ejercicio)
    }

    override fun getItemCount(): Int {
        return ejercicios.size
    }

    fun getEjercicios(): List<EjercicioRutina> {
        return ejercicios
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombre: TextView
        var tipo : TextView
        var cantidad : TextView
        init {
            nombre = itemView.findViewById(R.id.nombreEjercicio)
            tipo = itemView.findViewById(R.id.tipoEjercicio)
            cantidad = itemView.findViewById(R.id.cantidad)
        }
        fun bind(ejercicioRutina: EjercicioRutina) {
            nombre.text = ejercicioRutina.ejercicio.nombre
            tipo.text = ejercicioRutina.ejercicio.tipo
            var textoCardio = "Minutos : ${ejercicioRutina.minutos}\n" +
                    "Kilometros : ${ejercicioRutina.kilometrosRecorridos}"
            var textoNormal = "Series : ${ejercicioRutina.seriesEjercicio}\n" +
                    "Repeticiones : ${ejercicioRutina.repeticionesEjercicio}"
            if(tipo.text == "Cardio"){
                cantidad.text = textoCardio
            }else{
                cantidad.text =textoNormal
            }
        }
    }
}