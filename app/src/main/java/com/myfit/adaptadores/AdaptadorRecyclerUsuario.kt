package com.myfit.adaptadores

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R

private const val ELEMENTO_UNO = 1
class AdaptadorRecyclerUsuario internal constructor(val datos: List<String>?) :
    RecyclerView.Adapter<AdaptadorRecyclerUsuario.Holder>(),View.OnClickListener{
    lateinit var listenerClick:View.OnClickListener;

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        return if(i == ELEMENTO_UNO){
            val itemView: View = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.linea_uno_fragment_usuario, viewGroup, false)
            itemView.setOnClickListener(this)
            HolderGrande(itemView)

        }else{
            val itemView: View = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.linea_fragment_usuario, viewGroup, false)
            itemView.setOnClickListener(this)
            Holder(itemView)

        }

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val element= datos?.get(position)
        holder.bind(element)
    }
    override fun getItemCount(): Int {
        return datos?.size?:0
    }

    fun onClick(listener:View.OnClickListener)
    {
        this.listenerClick=listener
    }
    override fun onClick(p0: View?) {
        listenerClick?.onClick(p0)
    }

    open inner class Holder(v: View) : RecyclerView.ViewHolder(v) {
        val texto: TextView


        open fun bind(text:String?) {
            texto.text = text
            texto.textSize = 20f
            val layoutParams = texto.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = 15
            texto.layoutParams = layoutParams
        }
        init {
            texto = v.findViewById(R.id.texto)
        }
    }
    inner class HolderGrande(v: View) : Holder(v) {
        val nombre: TextView
        val email: TextView


        override fun bind(text:String?) {
            nombre.text = text
            email.text = text
        }
        init {
            nombre = v.findViewById(R.id.nombre)
            email = v.findViewById(R.id.email)
        }
    }
}
