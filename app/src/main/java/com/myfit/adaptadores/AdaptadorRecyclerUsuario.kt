package com.myfit.adaptadores

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.modelo.Usuario

private const val ELEMENTO_UNO = 1
class AdaptadorRecyclerUsuario internal constructor(val datos: List<String>?, var usuario: Usuario) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener{
    lateinit var listenerClick:View.OnClickListener;
    lateinit var holder:RecyclerView.ViewHolder
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        var view: View

        when(i){
            0 ->{
                view =LayoutInflater.from(viewGroup.context).inflate(R.layout.linea_uno_fragment_usuario,viewGroup,false)
                holder = HolderGrande(view)
            }

            1 ->{
                view =LayoutInflater.from(viewGroup.context).inflate(R.layout.linea_fragment_usuario,viewGroup,false)
                holder = HolderPeque(view)

            }
        }
        return holder

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            0 ->(holder as HolderGrande).bind(usuario)
            1 ->(holder as HolderPeque).bind(datos?.get(position))
        }
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
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }
    inner class HolderPeque(v: View) : RecyclerView.ViewHolder(v) {
        val texto: TextView


        fun bind(text:String?) {
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
    inner class HolderGrande(v: View) : RecyclerView.ViewHolder(v) {
        val nombre: TextView
        val email: TextView


        fun bind(usuario: Usuario) {
            nombre.text = usuario.usuario
            email.text = usuario.email
        }
        init {
            nombre = v.findViewById(R.id.nombre)
            email = v.findViewById(R.id.email)
        }
    }


}
