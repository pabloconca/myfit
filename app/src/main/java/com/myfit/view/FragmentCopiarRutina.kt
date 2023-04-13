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
import com.google.android.material.textfield.TextInputEditText
import com.myfit.R
import com.myfit.adaptadores.AdaptadorEjercicios
import com.myfit.controladores.AppController
import com.myfit.modelo.EjercicioRutina
import com.myfit.modelo.Rutina
import com.myfit.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.Util

class FragmentCopiarRutina : Fragment() {
    lateinit var adaptador : AdaptadorEjercicios
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_copiar_rutina,container,false)
        var nombreRutina = view.findViewById<TextInputEditText>(R.id.nombreNuevaRutina)
        var recycler = view.findViewById<RecyclerView>(R.id.recyclerViewEjercicios)
        var rutina : Rutina? = arguments?.getParcelable("RutinaEdit")
        rutina?.let {
            nombreRutina.setText(it.nombre)
            adaptador = AdaptadorEjercicios(rutina.ejercicioRutinaCollection)
            recycler.adapter = adaptador
        }
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                if (rutina != null) {
                    rutina.id = 0
                    rutina.idUsuario = Utils.usuarioActual
                    AppController.insertarRutina(rutina)
                    val navController= NavHostFragment.findNavController(this@FragmentCopiarRutina)
                    if (navController.currentDestination?.id == R.id.fragmentCopiarRutina)
                        navController.navigate(R.id.action_fragmentCopiarRutina_to_fragmentRutina)
                }
            }

        }
        recycler.layoutManager=
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        return view
    }
}