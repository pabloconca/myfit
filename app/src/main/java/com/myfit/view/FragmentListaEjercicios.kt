package com.myfit.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import com.myfit.modelo.Ejercicio
import com.myfit.modelo.EjercicioRutina
import com.myfit.modelo.Rutina
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentListaEjercicios : Fragment() {
    var adaptador : AdaptadorListaEjercicios = AdaptadorListaEjercicios(mutableListOf())
    lateinit var recycler : RecyclerView
    var listaEjercicios: MutableList<Ejercicio> = mutableListOf()
    var listaFiltrada: MutableList<Ejercicio> = mutableListOf()
    var estaFiltrado = false
    lateinit var vista: View
    private val model:DataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_lista_ejercicios,container,false)
        vista = view
        val rutina : Rutina? = arguments?.getParcelable("RUTINALISTA")
        recycler = view.findViewById(R.id.recyclerListaEjercicios)
        CoroutineScope(Dispatchers.IO).launch {
            val listaAEliminar = mutableListOf<Ejercicio>()
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
            }
        }
        view.findViewById<ChipGroup>(R.id.chipGroup).setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.size == 0) {
                estaFiltrado = false
                recargar()
            } else {
                checkedIds.forEach {
                    val position = group.indexOfChild(view.findViewById<Chip>(it))
                    when (position) {
                        0 -> {
                            filtradoTipos("Calistenia")
                        }
                        1 -> {
                            filtradoTipos("Musculacion")
                        }
                        2 -> {
                            filtradoTipos("Cardio")
                        }
                        3 -> {
                            filtrarValoracion()
                        }
                    }
                }
            }
        }
        val updateObserver = Observer<EjercicioRutina?> {
            listaEjercicios.remove(it.ejercicio)
            recargar()
        }
        model.getEjercicioRutinaBorrarLista.observe(requireActivity(),updateObserver)

        recargar()
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
    private fun filtrarValoracion(){
        CoroutineScope(Dispatchers.IO).launch {
            val listaValoraciones = mutableListOf<Double>()
            listaEjercicios.forEach {
                val valoracion = AppController.getValoracionEjercicio(it.id)!!
                listaValoraciones.add(valoracion)
            }
            listaFiltrada = listaEjercicios.sortedByDescending { ejercicio ->
                listaValoraciones[listaEjercicios.indexOf(ejercicio)]
            } as MutableList<Ejercicio>
            withContext(Dispatchers.Main){
                recargar(listaFiltrada)
            }
        }
        estaFiltrado = true
    }
    private fun filtradoTipos(tipo : String){
        listaFiltrada = listaEjercicios.filter { it.tipo == tipo } as MutableList<Ejercicio>
        adaptador = AdaptadorListaEjercicios(listaFiltrada)
        recycler.adapter = adaptador
        recargar(listaFiltrada)
        estaFiltrado = true
    }
    private fun recargar(){
        adaptador = AdaptadorListaEjercicios(listaEjercicios)
        clickManager()
        recycler.adapter = adaptador
    }
    private fun recargar(lista : List<Ejercicio>){
        adaptador = AdaptadorListaEjercicios(lista)
        clickManager()
        recycler.adapter = adaptador
    }
    private fun clickManager(){
        adaptador.clickCorto(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val posicion=recycler.getChildAdapterPosition(p0!!)
                if(estaFiltrado)
                    model.setEjercicioDetalle(listaFiltrada[posicion])
                else
                    model.setEjercicioDetalle(listaEjercicios[posicion])
                val navController= NavHostFragment.findNavController(this@FragmentListaEjercicios)
                if (navController.currentDestination?.id == R.id.fragmentListaEjercicios)
                    navController.navigate(R.id.action_fragmentListaEjercicios_to_fragmentDetalleEjercicio)
                vista.findViewById<ChipGroup>(R.id.chipGroup).clearCheck()

            }

        })
        adaptador.onImagenListener(object : OnImagenListenerEjercicio {
            override fun setOnImagenListener(ejercicio : Ejercicio) {
                if(ejercicio.tipo == "Cardio"){
                    model.setEjercicio(ejercicio)
                    val dialogo = DialogoEditarEjercicioRutinaCardio()
                    dialogo.show(parentFragmentManager,"DialogoEditarEjercicioRutinaCardio")
                }else{
                    model.setEjercicio(ejercicio)
                    val dialogo = DialogoEditarEjercicioRutina()
                    dialogo.show(parentFragmentManager,"DialogoEditarEjercicioRutina")
                }

            }
        })
    }
}