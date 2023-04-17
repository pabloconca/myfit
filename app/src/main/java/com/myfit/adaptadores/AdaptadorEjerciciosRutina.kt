package com.myfit.adaptadores

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.interfaces.OnImagenListener
import com.myfit.modelo.EjercicioRutina

class AdaptadorEjerciciosRutina(private val ejercicios: List<EjercicioRutina>?) : RecyclerView.Adapter<AdaptadorEjerciciosRutina.ViewHolder>(),
    View.OnClickListener{
    lateinit var listenerClick: View.OnClickListener
    lateinit var listenerImagen: OnImagenListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.linea_ejercicio, parent, false)
        view.setOnClickListener(this)
        var holder = ViewHolder(view)
        holder.onImagenListener(object :OnImagenListener{
            override fun setOnImagenListener(ejer:EjercicioRutina) {
                listenerImagen.setOnImagenListener(ejer)
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

    fun getEjercicios(): List<EjercicioRutina>? {
        return ejercicios
    }
    fun clickCorto(listener:View.OnClickListener)
    {
        this.listenerClick=listener
    }
    override fun onClick(p0: View?) {
        listenerClick.onClick(p0)
    }
    fun onImagenListener(interfaz:OnImagenListener){
        this.listenerImagen = interfaz
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var interfaz:OnImagenListener
        var nombre: TextView
        var tipo : TextView
        var cantidad : TextView
        var imagen : ImageView
        lateinit var ejercicio : EjercicioRutina
        init {
            nombre = itemView.findViewById(R.id.nombreEjercicio)
            tipo = itemView.findViewById(R.id.tipoEjercicio)
            cantidad = itemView.findViewById(R.id.cantidad)
            imagen = itemView.findViewById(R.id.imagen)
            imagen.setOnClickListener{
                interfaz.setOnImagenListener(ejercicio)
            }
        }
        fun bind(ejercicioRutina: EjercicioRutina) {
            ejercicio = ejercicioRutina
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
        fun onImagenListener(interfaz:OnImagenListener){
            this.interfaz = interfaz
        }
    }

}