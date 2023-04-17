package com.myfit.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.adaptadores.AdaptadorListaEjercicios
import com.myfit.controladores.AppController
import com.myfit.modelo.Ejercicio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentListaEjercicios : Fragment() {
    lateinit var adaptador : AdaptadorListaEjercicios
    lateinit var recycler : RecyclerView
    lateinit var listaEjercicios: MutableList<Ejercicio>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_lista_ejercicios,container,false)
        recycler = view.findViewById(R.id.recyclerListaEjercicios)
        CoroutineScope(Dispatchers.IO).launch {
            listaEjercicios = AppController.getEjercicios()!!
            withContext(Dispatchers.Main){
                adaptador = AdaptadorListaEjercicios(listaEjercicios)
                recycler.adapter = adaptador
            }

        }
        view.findViewById<EditText>(R.id.et_search).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase()
                val filteredList = mutableListOf<Ejercicio>()

                for (ejercicio in listaEjercicios) {
                    if (ejercicio.nombre.lowercase().contains(query)) {
                        filteredList.add(ejercicio)
                    }
                }
                adaptador = AdaptadorListaEjercicios(filteredList)
                recycler.adapter = adaptador
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        recycler.layoutManager=
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        return view
    }

}