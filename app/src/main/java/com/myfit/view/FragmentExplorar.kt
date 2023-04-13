package com.myfit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.adaptadores.AdaptadorRecyclerExplorar
import com.myfit.adaptadores.AdaptadorRecyclerRutina
import com.myfit.controladores.AppController
import com.myfit.modelo.Rutina
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentExplorar : Fragment() {
    lateinit var recycler : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_explorar,container,false)
        recycler = view.findViewById(R.id.recycler)
        CoroutineScope(Dispatchers.IO).launch {
            var rutinasList = AppController.getRutinas()
            withContext(Dispatchers.Main){
                var adapter = AdaptadorRecyclerExplorar(rutinasList)
                recycler.adapter = adapter
                clickManager(adapter,rutinasList)
            }
        }

        recycler.layoutManager=
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        return view
    }
    private fun clickManager(adaptador : AdaptadorRecyclerExplorar,listaRutinas : MutableList<Rutina>?){
        val navController= NavHostFragment.findNavController(this)
        adaptador.clickCorto(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var posicion=recycler.getChildAdapterPosition(p0!!)
                var rutina = listaRutinas?.get(posicion)
                var bundle = Bundle()
                bundle.putParcelable("RutinaEdit",rutina)
                if (rutina != null && navController.currentDestination?.id == R.id.fragmentExplorar){
                    navController.navigate(R.id.action_fragmentExplorar_to_fragmentCopiarRutina,bundle)
                }
            }

        })
    }
}