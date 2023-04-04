package com.myfit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
        //var adaptador = AdaptadorRecyclerRutina()
        var recycler = view.findViewById<RecyclerView>(R.id.recycler)
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
            val navController= NavHostFragment.findNavController(this)
            if (navController.currentDestination?.id == R.id.fragmentRutina)
                navController.navigate(R.id.action_fragmentRutina_to_fragmentCrearRutina)
            }
        //recycler.adapter = adaptador
        recycler.layoutManager=
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        return view
    }
}