package com.myfit.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.interfaces.OnImagenListenerEjercicioRutina
import com.myfit.modelo.EjercicioRutina

class AdaptadorEjerciciosRutinaCopiar(private val ejercicios: List<EjercicioRutina>?) : RecyclerView.Adapter<AdaptadorEjerciciosRutinaCopiar.ViewHolder>(),
    View.OnLongClickListener{
    lateinit var listenerLargo: View.OnLongClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.linea_ejercicio_copia, parent, false)
        view.setOnLongClickListener(this)
        return ViewHolder(view)
    }
    override fun onLongClick(p0: View?): Boolean {
        listenerLargo.onLongClick(p0)
        return true
    }

    fun clickLargo(listener: View.OnLongClickListener) {
        this.listenerLargo = listener
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var interfaz: OnImagenListenerEjercicioRutina
        var nombre: TextView
        var tipo : TextView
        var cantidad : TextView
        lateinit var ejercicio : EjercicioRutina
        init {
            nombre = itemView.findViewById(R.id.nombreEjercicio)
            tipo = itemView.findViewById(R.id.tipoEjercicio)
            cantidad = itemView.findViewById(R.id.cantidad)
        }
        fun bind(ejercicioRutina: EjercicioRutina) {
            ejercicio = ejercicioRutina
            nombre.text = ejercicioRutina.ejercicio.nombre
            tipo.text = ejercicioRutina.ejercicio.tipo
            val textoCardio = "Minutos : ${ejercicioRutina.minutos}\n" +
                    "Kilometros : ${ejercicioRutina.kilometrosRecorridos}"
            val textoNormal = "Series : ${ejercicioRutina.seriesEjercicio}\n" +
                    "Repeticiones : ${ejercicioRutina.repeticionesEjercicio}"
            if(tipo.text == "Cardio"){
                cantidad.text = textoCardio
            }else{
                cantidad.text =textoNormal
            }
        }
        fun onImagenListener(interfaz: OnImagenListenerEjercicioRutina){
            this.interfaz = interfaz
        }
    }



}