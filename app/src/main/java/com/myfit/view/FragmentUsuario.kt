package com.myfit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.adaptadores.AdaptadorRecyclerUsuario
import com.myfit.modelo.Usuario

class FragmentUsuario : Fragment(){
    lateinit var adaptador : AdaptadorRecyclerUsuario
    lateinit var recycler : RecyclerView
    lateinit var vista : View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista=inflater.inflate(R.layout.fragment_usuario,container,false)
        val settingsList = listOf("","Editar contraseña", "Eliminar cuenta", "Cerrar sesión")
        var usuario = Usuario("1","pablo@pablo","a","pablo ");
        adaptador = AdaptadorRecyclerUsuario(settingsList,usuario)
        recycler = vista.findViewById(R.id.recycler)
        clickManager()

        recycler.adapter = adaptador
        recycler.layoutManager=
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        return vista
    }

    fun clickManager(){
        val navController= NavHostFragment.findNavController(this)
        adaptador.clickCorto(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var posicion=recycler.getChildAdapterPosition(p0!!)
                if (navController.currentDestination?.id == R.id.fragmentUsuario)
                    navController.navigate(R.id.action_fragmentUsuario_to_fragmentEditarPassword)
            }

        })
    }

}