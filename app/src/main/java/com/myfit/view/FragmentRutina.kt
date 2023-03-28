package com.myfit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.adaptadores.AdaptadorRecyclerRutina
import com.myfit.controladores.RutinaController

class FragmentRutina : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_container_recycler_rutina,container,false)
        //var adaptador = AdaptadorRecyclerRutina(RutinaController.usuario)
        var recycler = view.findViewById<RecyclerView>(R.id.recycler)
        //TODO
       // recycler.adapter = adaptador
        recycler.layoutManager=
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        return view
    }
}