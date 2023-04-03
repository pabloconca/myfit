package com.myfit.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import com.myfit.R
import com.myfit.holder.HolderExplorar
import com.myfit.modelo.Rutina

class AdaptadorRecyclerExplorar(val datos : MutableList<Rutina>?) : RecyclerView.Adapter<HolderExplorar>(),
    View.OnClickListener {
    lateinit var listenerClick:View.OnClickListener;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderExplorar {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.linea_fragment_explorar, parent, false)
        itemView.setOnClickListener(this)
        return HolderExplorar(itemView)
    }

    override fun onBindViewHolder(holder: HolderExplorar, position: Int) {
        val element= datos?.get(position)
        holder.bind(element)
    }

    override fun getItemCount(): Int {
        return datos?.size ?: 0
    }

    fun onClick(listener:View.OnClickListener)
    {
        this.listenerClick=listener
    }
    override fun onClick(p0: View?) {
        listenerClick.onClick(p0)
    }

}