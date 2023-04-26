package com.myfit.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.interfaces.OnImagenListenerEjercicio
import com.myfit.modelo.Ejercicio

class AdaptadorListaEjercicios(private val ejercicios: List<Ejercicio>?) : RecyclerView.Adapter<AdaptadorListaEjercicios.ViewHolder>(),
    View.OnClickListener{
    lateinit var listenerClick: View.OnClickListener
    lateinit var listenerImagen: OnImagenListenerEjercicio

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.linea_ejercicio_lista, parent, false)
        view.setOnClickListener(this)
        val holder = ViewHolder(view)

        holder.onImagenListener(object :OnImagenListenerEjercicio{
            override fun setOnImagenListener(ejercicio:Ejercicio) {
                listenerImagen.setOnImagenListener(ejercicio)
            }
        })
        return holder
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

    fun clickCorto(listener: View.OnClickListener)
    {
        this.listenerClick=listener
    }
    override fun onClick(p0: View?) {
        listenerClick.onClick(p0)
    }
    fun onImagenListener(interfaz:OnImagenListenerEjercicio){
        this.listenerImagen = interfaz
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var interfaz:OnImagenListenerEjercicio
        var nombre : TextView
        var descripcion : TextView
        var imagen : ImageView
        lateinit var ejercicioHolder : Ejercicio

        init {
            nombre = itemView.findViewById(R.id.nombre)
            descripcion = itemView.findViewById(R.id.descripcion)
            imagen = itemView.findViewById(R.id.addEjercicioToRutina)
            imagen.setOnClickListener{
                interfaz.setOnImagenListener(ejercicioHolder)
            }
        }
        fun bind(ejercicio: Ejercicio) {
            ejercicioHolder = ejercicio
            nombre.text = ejercicio.nombre
            descripcion.text = ejercicio.descripcion
        }
        fun onImagenListener(interfaz:OnImagenListenerEjercicio){
            this.interfaz = interfaz
        }
    }

}
