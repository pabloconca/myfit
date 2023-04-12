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
import com.myfit.R
import com.myfit.adaptadores.AdaptadorRecyclerRutina
import com.myfit.controladores.AppController
import com.myfit.modelo.Rutina
import com.myfit.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentRutina : Fragment() {
    lateinit var recycler : RecyclerView
    private val model:DataViewModel by activityViewModels()
    var listaRutinas : MutableList<Rutina>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_container_recycler_rutina,container,false)
        recycler = view.findViewById<RecyclerView>(R.id.recycler)
        if(!Utils.estaLogeado){
            Utils.mostrarDialogoInicioSesion(parentFragmentManager)
        }
        val updateObserver = Observer<Boolean> { it ->
            if (it) {
                cargarRutinasDelUsuario()
                model.setTieneQueActualizarRutinas(false)
            }
        }
        model.getTieneQueActualizarRutinas.observe(requireActivity(),updateObserver)
        if(Utils.estaLogeado){
            cargarRutinasDelUsuario()
        }

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
            val navController= NavHostFragment.findNavController(this)
            if (navController.currentDestination?.id == R.id.fragmentRutina)
                navController.navigate(R.id.action_fragmentRutina_to_fragmentCrearRutina)
        }
        recycler.layoutManager=
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        return view
    }
    private fun clickManager(adaptador : AdaptadorRecyclerRutina){
        val navController= NavHostFragment.findNavController(this)
        adaptador.clickCorto(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var posicion=recycler.getChildAdapterPosition(p0!!)
                var rutina = listaRutinas?.get(posicion)
                var bundle = Bundle()
                bundle.putParcelable("RutinaEdit",rutina)
                if (rutina != null && navController.currentDestination?.id == R.id.fragmentRutina){
                    navController.navigate(R.id.action_fragmentRutina_to_fragmentCrearRutina,bundle)
                }
            }

        })
    }

    fun cargarRutinasDelUsuario(){
        CoroutineScope(Dispatchers.IO).launch {
            listaRutinas = AppController.findRutinasFromUser(Utils.usuarioActual.id)
            withContext(Dispatchers.Main){
                var adaptador = AdaptadorRecyclerRutina(listaRutinas)
                recycler.adapter = adaptador
                clickManager(adaptador)
            }
        }
    }
}
