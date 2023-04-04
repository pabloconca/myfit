package com.myfit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.adaptadores.AdaptadorRecyclerUsuario
import com.myfit.modelo.Usuario

class FragmentUsuario : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_usuario,container,false)
        val settingsList = listOf("","Editar contraseña", "Eliminar cuenta", "Cerrar sesión")
        var usuario = Usuario("1","pablo@pablo","a","pablo ");
        var adaptador = AdaptadorRecyclerUsuario(settingsList,usuario)
        var recycler = view.findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = adaptador
        recycler.layoutManager=
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        return view
    }
}