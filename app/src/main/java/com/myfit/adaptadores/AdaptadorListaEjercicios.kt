package com.myfit.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.modelo.Ejercicio
import com.myfit.modelo.EjercicioRutina

class AdaptadorListaEjercicios(private val ejercicios: List<Ejercicio>?) : RecyclerView.Adapter<AdaptadorListaEjercicios.ViewHolder>(),
    View.OnClickListener{
    lateinit var listenerClick: View.OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.linea_ejercicio_lista, parent, false)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ejercicio = ejercicios?.get(position)
        if (ejercicio != null) {
            holder.bind(ejercicio)
        }
    }

    override fun getItemCount(): Int {
        return ejercicios?.size ?: 0
    }

    fun getEjercicios(): List<Ejercicio>? {
        return ejercicios
    }
    fun clickCorto(listener: View.OnClickListener)
    {
        this.listenerClick=listener
    }
    override fun onClick(p0: View?) {
        listenerClick.onClick(p0)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombre : TextView
        init {
            nombre = itemView.findViewById(R.id.nombre)
        }
        fun bind(ejercicio: Ejercicio) {
            nombre.text = ejercicio.nombre
        }

    }

}
