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
import com.myfit.modelo.EjercicioRutina
import com.myfit.modelo.EjercicioRutinaPK
import com.myfit.modelo.Rutina
import com.myfit.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentCopiarRutina : Fragment() {
    lateinit var adaptador : AdaptadorEjerciciosRutina
    lateinit var recycler : RecyclerView
    lateinit var listaEjercicios : MutableList<EjercicioRutina>
    private val model:DataViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_copiar_rutina,container,false)
        var nombreRutina = view.findViewById<TextInputEditText>(R.id.nombreNuevaRutina)
        recycler = view.findViewById<RecyclerView>(R.id.recyclerViewEjercicios)
        var rutina : Rutina? = arguments?.getParcelable("RutinaEdit")
        rutina?.let {
            nombreRutina.setText(it.nombre)
            adaptador = AdaptadorEjerciciosRutina(rutina.ejercicioRutinaCollection)
            recycler.adapter = adaptador
            listaEjercicios = adaptador.getEjercicios() as MutableList<EjercicioRutina>

        }
        clickManager()
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                if (rutina != null) {
                    rutina.id = 0
                    rutina.idUsuario = Utils.usuarioActual
                    var lista = rutina.ejercicioRutinaCollection
                    rutina.ejercicioRutinaCollection = lista
                    AppController.insertarRutina(rutina)
                    val navController= NavHostFragment.findNavController(this@FragmentCopiarRutina)
                    if (navController.currentDestination?.id == R.id.fragmentCopiarRutina)
                        navController.navigate(R.id.action_fragmentCopiarRutina_to_fragmentExplorar)
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


            }
        })
    }
}