package com.myfit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.myfit.R
import com.myfit.adaptadores.AdaptadorEjerciciosRutina
import com.myfit.controladores.AppController
import com.myfit.interfaces.OnImagenListener
import com.myfit.modelo.EjercicioRutina
import com.myfit.modelo.Rutina
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentCrearRutina : Fragment() {
    lateinit var adaptador : AdaptadorEjerciciosRutina
    lateinit var recycler : RecyclerView
    lateinit var listaEjercicios : MutableList<EjercicioRutina>
    private val model:DataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_crear_rutina,container,false)
        var nombreRutina = view.findViewById<TextInputEditText>(R.id.nombreNuevaRutina)
        recycler = view.findViewById(R.id.recyclerViewEjercicios)
        var rutina : Rutina? = arguments?.getParcelable("RutinaEdit")
        if(rutina != null){
            rutina?.let {
                nombreRutina.setText(it.nombre)
                adaptador = AdaptadorEjerciciosRutina(rutina.ejercicioRutinaCollection)
                recycler.adapter = adaptador
            }
            listaEjercicios = adaptador.getEjercicios() as MutableList<EjercicioRutina>
        }else{
            var lista: List<EjercicioRutina>? = null
            adaptador = AdaptadorEjerciciosRutina(lista)
        }

        clickManager()
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
            var nombreRutinaNueva =
                view.findViewById<TextInputEditText>(R.id.nombreNuevaRutina).text.toString()
            listaEjercicios = adaptador.getEjercicios() as MutableList<EjercicioRutina>
            CoroutineScope(Dispatchers.Main).launch {
                if (rutina != null) {
                    rutina.nombre = nombreRutinaNueva
                    rutina.ejercicioRutinaCollection = listaEjercicios
                    AppController.updateRutina(rutina)
                    val navController= NavHostFragment.findNavController(this@FragmentCrearRutina)
                    if (navController.currentDestination?.id == R.id.fragmentCrearRutina)
                        navController.navigate(R.id.action_fragmentCrearRutina_to_fragmentRutina)
                }
            }
        }

        recycler.layoutManager=
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        return view
    }
    private fun clickManager(){
        adaptador.clickCorto(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var posicion=recycler.getChildAdapterPosition(p0!!)
                var ejercicioRutina = listaEjercicios[posicion]
                model.setEjercicioRutina(ejercicioRutina)
                if(ejercicioRutina.ejercicio.tipo == "Cardio"){

                }else{
                    var dialogo = DialogoEditarEjercicioRutina()
                    dialogo.show(parentFragmentManager,"DialogoEditarEjercicioRutina")
                    val updateObserver = Observer<EjercicioRutina> { it ->
                        listaEjercicios.remove(ejercicioRutina)
                        ejercicioRutina = it
                        listaEjercicios.add(ejercicioRutina)
                        recargar()
                    }
                    model.getEjercicioRutina.observe(requireActivity(),updateObserver)
                }


            }

        })
        adaptador.onImagenListener(object : OnImagenListener{
            override fun setOnImagenListener(ejercicio : EjercicioRutina) {
                listaEjercicios.remove(ejercicio)
                recargar()
            }
        })
    }
    private fun recargar(){
        adaptador = AdaptadorEjerciciosRutina(listaEjercicios)
        recycler.adapter = adaptador
    }
}