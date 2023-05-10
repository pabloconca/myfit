package com.myfit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.myfit.R
import com.myfit.adaptadores.AdaptadorEjerciciosRutina
import com.myfit.controladores.AppController
import com.myfit.modelo.EjercicioRutina
import com.myfit.modelo.Rutina
import com.myfit.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentCopiarRutina : Fragment() {
    lateinit var adaptador : AdaptadorEjerciciosRutina
    lateinit var recycler : RecyclerView
    private val model:DataViewModel by activityViewModels()
    lateinit var listaEjercicios : MutableList<EjercicioRutina>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_copiar_rutina,container,false)
        val nombreRutina = view.findViewById<TextInputEditText>(R.id.nombreNuevaRutina)
        recycler = view.findViewById(R.id.recyclerViewEjercicios)
        val rutina : Rutina? = arguments?.getParcelable("RutinaEdit")
        rutina?.let {
            nombreRutina.setText(it.nombre)
            adaptador = AdaptadorEjerciciosRutina(rutina.ejercicioRutinaCollection)
            recycler.adapter = adaptador
            listaEjercicios = rutina.ejercicioRutinaCollection as MutableList<EjercicioRutina>

        }
        clickManager()
        view.findViewById<Button>(R.id.buttonAdd).setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                if (rutina != null) {
                    rutina.id = 0
                    rutina.idUsuario = Utils.usuarioActual
                    val lista = rutina.ejercicioRutinaCollection//TODO quitar estas dos lineas
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
        adaptador.clickLargo(object : View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                val posicion=recycler.getChildAdapterPosition(p0!!)
                model.setEjercicioDetalle(listaEjercicios[posicion].ejercicio)
                val navController= NavHostFragment.findNavController(this@FragmentCopiarRutina)
                if (navController.currentDestination?.id == R.id.fragmentCopiarRutina)
                    navController.navigate(R.id.action_fragmentCopiarRutina_to_fragmentDetalleEjercicio)
                return true
            }

        })
    }
}