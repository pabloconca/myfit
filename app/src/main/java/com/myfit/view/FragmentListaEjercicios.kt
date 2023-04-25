package com.myfit.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.myfit.R
import com.myfit.adaptadores.AdaptadorListaEjercicios
import com.myfit.controladores.AppController
import com.myfit.interfaces.OnImagenListenerEjercicio
import com.myfit.interfaces.OnImagenListenerEjercicioRutina
import com.myfit.modelo.Ejercicio
import com.myfit.modelo.EjercicioRutina
import com.myfit.modelo.Rutina
import com.myfit.modelo.UsuarioValoraEjercicio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentListaEjercicios : Fragment() {
    var adaptador : AdaptadorListaEjercicios = AdaptadorListaEjercicios(mutableListOf())
    lateinit var recycler : RecyclerView
    var listaEjercicios: MutableList<Ejercicio> = mutableListOf()
    private val model:DataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_lista_ejercicios,container,false)
        var rutina : Rutina? = arguments?.getParcelable("RUTINALISTA")
        recycler = view.findViewById(R.id.recyclerListaEjercicios)
        CoroutineScope(Dispatchers.IO).launch {
            var listaAEliminar = mutableListOf<Ejercicio>()
            listaEjercicios = AppController.getEjercicios()!!
            listaEjercicios.forEach { ejercicio ->
                rutina?.ejercicioRutinaCollection?.forEach {
                    if(ejercicio.id == it.ejercicio.id){
                        listaAEliminar.add(ejercicio)
                    }

                }
            }
            listaAEliminar.forEach {
                listaEjercicios.remove(it)
            }
            withContext(Dispatchers.Main){
                recargar()
                clickManager()
            }
        }
        view.findViewById<ChipGroup>(R.id.chipGroup).setOnCheckedStateChangeListener { group, checkedIds ->

            checkedIds.forEach {
                val position = group.indexOfChild(view.findViewById<Chip>(it))
                //TODO metodo para no repetir codigo
                when(position){
                    0 -> {
                        filtradoTipos("Calistenia")
                    }
                    1 -> {
                        filtradoTipos("Musculacion")
                    }
                    2 -> {
                        filtradoTipos("Cardio")
                    }
                }
            }
        }
        view.findViewById<Chip>(R.id.Valoracion).setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                var lista = adaptador.getEjercicios()
                if (lista != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        var listaValoraciones = mutableListOf<Double>()
                        lista.forEach {
                            val valoracion = AppController.getValoracionEjercicio(it.id)!!
                            listaValoraciones.add(valoracion)
                        }
                        val listaOrdenada = lista.sortedByDescending { ejercicio ->
                            listaValoraciones[lista.indexOf(ejercicio)]
                        }
                        withContext(Dispatchers.Main){
                            adaptador = AdaptadorListaEjercicios(listaOrdenada)
                            recycler.adapter = adaptador
                        }
                    }
                }
            }else{
                recargar()
            }
        }
        val updateObserver = Observer<EjercicioRutina> {
            listaEjercicios.remove(it.ejercicio)
            recargar()
        }
        model.getEjercicioRutinaAdd.observe(requireActivity(),updateObserver)

        clickManager()
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
    private fun filtradoTipos(tipo : String){
        val listaNueva = listaEjercicios.filter { it.tipo == tipo }
        adaptador = AdaptadorListaEjercicios(listaNueva)
        recycler.adapter = adaptador
    }
    private fun recargar(){
        adaptador = AdaptadorListaEjercicios(listaEjercicios)
        recycler.adapter = adaptador
    }
    private fun clickManager(){
        adaptador.clickCorto(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val posicion=recycler.getChildAdapterPosition(p0!!)
                model.setEjercicioDetalle(listaEjercicios[posicion])
                val navController= NavHostFragment.findNavController(this@FragmentListaEjercicios)
                if (navController.currentDestination?.id == R.id.fragmentListaEjercicios)
                    navController.navigate(R.id.action_fragmentListaEjercicios_to_fragmentDetalleEjercicio)

            }

        })
        adaptador.onImagenListener(object : OnImagenListenerEjercicio {
            override fun setOnImagenListener(ejercicio : Ejercicio) {
                if(ejercicio.tipo == "Cardio"){

                }else{
                    model.setEjercicio(ejercicio)
                    val dialogo = DialogoEditarEjercicioRutina()
                    dialogo.show(parentFragmentManager,"DialogoEditarEjercicioRutina")
                }

            }
        })
    }
    private fun volver(){
        val navController= NavHostFragment.findNavController(this@FragmentListaEjercicios)
        if (navController.currentDestination?.id == R.id.fragmentListaEjercicios)
            navController.navigate(R.id.action_fragmentListaEjercicios_to_fragmentCrearRutina)
    }
}