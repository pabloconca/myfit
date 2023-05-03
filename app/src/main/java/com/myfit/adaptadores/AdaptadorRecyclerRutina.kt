package com.myfit.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.holder.HolderExplorar
import com.myfit.holder.HolderRutina
import com.myfit.modelo.Rutina

class AdaptadorRecyclerRutina(val datos : MutableList<Rutina>?) : RecyclerView.Adapter<HolderRutina>(),
    View.OnClickListener,View.OnLongClickListener {
    lateinit var listenerClick: View.OnClickListener
    lateinit var listenerLargo: View.OnLongClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderRutina {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.linea_recycler_rutina, parent, false)
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
        return HolderRutina(itemView)
    }

    override fun onBindViewHolder(holder: HolderRutina, position: Int) {
        val element= datos?.get(position)
        holder.bind(element)
    }

    override fun onLongClick(p0: View?): Boolean {
        listenerLargo.onLongClick(p0)
        return true
    }

    fun clickLargo(listener: View.OnLongClickListener) {
        this.listenerLargo = listener
    }
    override fun getItemCount(): Int {
        return datos?.size ?: 0
    }

    fun clickCorto(listener:View.OnClickListener)
    {
        this.listenerClick=listener
    }
    override fun onClick(p0: View?) {
        listenerClick.onClick(p0)
    }
}