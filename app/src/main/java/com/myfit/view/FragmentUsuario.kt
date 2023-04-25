package com.myfit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.adaptadores.AdaptadorRecyclerUsuario
import com.myfit.modelo.Usuario
import com.myfit.utils.Utils

class FragmentUsuario : Fragment(){
    lateinit var adaptador : AdaptadorRecyclerUsuario
    lateinit var recycler : RecyclerView
    private val model:DataViewModel by activityViewModels()
    val settingsList = listOf("","Editar contraseña", "Eliminar cuenta", "Cerrar sesión")
    lateinit var vista : View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista=inflater.inflate(R.layout.fragment_usuario,container,false)
        var usuario = Utils.getUser()
        adaptador = AdaptadorRecyclerUsuario(settingsList,usuario)
        recycler = vista.findViewById(R.id.recycler)
        clickManager()
        val updateObserver = Observer<Boolean> { it ->
            if (it) {
                recargarDatos()
                clickManager()
                model.setTieneQueActualizarUser(false)
            }
        }
        model.getTieneQueActualizarUser.observe(requireActivity(),updateObserver)
        recycler.adapter = adaptador
        recycler.layoutManager=
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        return vista
    }

    private fun recargarDatos(){
        var usuario = Utils.getUser()
        adaptador = AdaptadorRecyclerUsuario(settingsList,usuario)
        recycler.adapter = adaptador
    }
    private fun clickManager(){
        val navController= NavHostFragment.findNavController(this)
        adaptador.clickCorto(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var posicion=recycler.getChildAdapterPosition(p0!!)
                if(posicion == 0 && navController.currentDestination?.id == R.id.fragmentUsuario){
                    navController.navigate(R.id.action_fragmentUsuario_to_fragmentEditarUsuario)
                }else if (posicion == 1 && navController.currentDestination?.id == R.id.fragmentUsuario){
                    navController.navigate(R.id.action_fragmentUsuario_to_fragmentEditarPassword)
                }else if(posicion == 2 && navController.currentDestination?.id == R.id.fragmentUsuario){
                    var dialogoEliminarUser = DialogoEliminarUsuario()
                    dialogoEliminarUser.show(parentFragmentManager,"DialogoEliminarUsuario")
                }else if(posicion == 3 && navController.currentDestination?.id == R.id.fragmentUsuario){
                    var dialogoCerrarSesion = DialogoCerrarSesion()
                    dialogoCerrarSesion.show(parentFragmentManager,"DialogoCerrarSesion")
                }

            }

        })
    }

}