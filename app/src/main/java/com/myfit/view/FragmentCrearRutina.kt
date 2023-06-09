package com.myfit.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.myfit.R
import com.myfit.adaptadores.AdaptadorEjerciciosRutina
import com.myfit.controladores.AppController
import com.myfit.interfaces.OnImagenListenerEjercicioRutina
import com.myfit.modelo.EjercicioRutina
import com.myfit.modelo.EjercicioRutinaPK
import com.myfit.modelo.Rutina
import com.myfit.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentCrearRutina : Fragment() {
    lateinit var adaptador : AdaptadorEjerciciosRutina
    lateinit var recycler : RecyclerView
    var listaEjercicios = mutableListOf<EjercicioRutina>()
    private val model:DataViewModel by activityViewModels()
    var listaAdd : MutableList<EjercicioRutina> = mutableListOf()
    var rutina = Rutina(0,Utils.usuarioActual,"",listaEjercicios)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_crear_rutina,container,false)
        val nombreRutina = view.findViewById<EditText>(R.id.nombreNuevaRutina)
        recycler = view.findViewById(R.id.recyclerViewEjercicios)
        val rutinaEdit : Rutina? = arguments?.getParcelable("RutinaEdit")
        if(rutinaEdit != null){
            rutina = rutinaEdit
        }
        rutina.let {
            nombreRutina.setText(it.nombre)
            adaptador = AdaptadorEjerciciosRutina(rutina.ejercicioRutinaCollection)
            recycler.adapter = adaptador
        }
        listaEjercicios = rutina.ejercicioRutinaCollection as MutableList<EjercicioRutina>
        if(listaAdd.isNotEmpty()){
            listaAdd.forEach { ejercicio ->
                if(!listaEjercicios.contains(ejercicio)) {
                    ejercicio.ejercicioRutinaPK = EjercicioRutinaPK(ejercicio.ejercicio.id,rutina.id)
                    listaEjercicios.add(ejercicio)
                }
            }
            listaAdd.clear()
        }
        model.setEjercicioRutinaAdd(null)

        val updateObserver = Observer<EjercicioRutina?> {
            if (it != null && !listaEjercicios.contains(it)) {
                it.id = rutina.id
                listaAdd.add(it)
            }
        }
        model.getEjercicioRutinaAdd.observe(requireActivity(),updateObserver)

        recargar()
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
            val nombreRutinaNueva =
                view.findViewById<EditText>(R.id.nombreNuevaRutina).text.toString()
            val ilNombre = view.findViewById<TextInputLayout>(R.id.nombreNuevaRutinaIl)

            listaEjercicios = rutina.ejercicioRutinaCollection as MutableList<EjercicioRutina>
            CoroutineScope(Dispatchers.Main).launch {
                if(nombreRutinaNueva.isNotEmpty()){
                    rutina.nombre = nombreRutinaNueva
                    rutina.ejercicioRutinaCollection = listaEjercicios
                    if(rutinaEdit != null){
                        AppController.updateRutina(rutina)
                    }else{
                        AppController.insertarRutina(rutina)
                    }
                    val navController= NavHostFragment.findNavController(this@FragmentCrearRutina)
                    if (navController.currentDestination?.id == R.id.fragmentCrearRutina)
                        navController.navigate(R.id.action_fragmentCrearRutina_to_fragmentRutina)
                }else{
                    withContext(Dispatchers.Main){
                        ilNombre.error = "Debes de introducir un nombre a la rutina"
                    }
                }
            }
        }
        view.findViewById<FloatingActionButton>(R.id.addEjercicio).setOnClickListener{
            rutina.ejercicioRutinaCollection = listaEjercicios
            val bundle = Bundle()
            bundle.putParcelable("RUTINALISTA",rutina)
            val navController= NavHostFragment.findNavController(this@FragmentCrearRutina)
            if (navController.currentDestination?.id == R.id.fragmentCrearRutina)
                navController.navigate(R.id.action_fragmentCrearRutina_to_fragmentListaEjercicios,bundle)
        }

        recycler.layoutManager=
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        return view
    }
    private fun clickManager(){
        adaptador.clickCorto(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val posicion=recycler.getChildAdapterPosition(p0!!)
                var ejercicioRutina = listaEjercicios[posicion]
                model.setEjercicioRutina(ejercicioRutina)
                if(ejercicioRutina.ejercicio.tipo == "Cardio"){
                    val dialogo = DialogoEditarEjercicioRutinaCardio()
                    val bundle = Bundle()
                    bundle.putBoolean("EDITAR",true)
                    dialogo.arguments = bundle
                    dialogo.show(parentFragmentManager,"DialogoEditarEjercicioRutinaCardio")
                    val updateObserver = Observer<EjercicioRutina?> {
                        if (it != null && it.ejercicioRutinaPK.idRutina == rutina.id) {
                            val indice = listaEjercicios.indexOfFirst { ejercicio -> ejercicio.ejercicio.id == it.ejercicio.id }
                            listaEjercicios[indice] = it
                            model.setEjercicioRutinaEditado(null)
                            recargar()
                        }

                    }
                    model.getEjercicioRutinaEditado.observe(requireActivity(),updateObserver)
                }else{
                    val dialogo = DialogoEditarEjercicioRutina()
                    val bundle = Bundle()
                    bundle.putBoolean("EDITAR",true)
                    dialogo.arguments = bundle
                    dialogo.show(parentFragmentManager,"DialogoEditarEjercicioRutina")
                    val updateObserver = Observer<EjercicioRutina?> {
                        if (it != null && it.ejercicioRutinaPK.idRutina == rutina.id) {
                            val indice = listaEjercicios.indexOfFirst { ejercicio -> ejercicio.ejercicio.id == it.ejercicio.id }
                            if(indice != -1){
                                listaEjercicios[indice] = it
                                recargar()
                            }
                        }
                    }

                    model.getEjercicioRutinaEditado.observe(requireActivity(),updateObserver)
                }


            }

        })
        adaptador.clickLargo(object : View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                val posicion=recycler.getChildAdapterPosition(p0!!)
                model.setEjercicioDetalle(listaEjercicios[posicion].ejercicio)
                val navController= NavHostFragment.findNavController(this@FragmentCrearRutina)
                if (navController.currentDestination?.id == R.id.fragmentCrearRutina)
                    navController.navigate(R.id.action_fragmentCrearRutina_to_fragmentDetalleEjercicio)
                return true
            }

        })
        adaptador.onImagenListener(object : OnImagenListenerEjercicioRutina{
            override fun setOnImagenListener(ejercicio : EjercicioRutina) {
                listaEjercicios.remove(ejercicio)
                recargar()
            }
        })
    }
    private fun recargar(){
        adaptador = AdaptadorEjerciciosRutina(listaEjercicios)
        clickManager()
        recycler.adapter = adaptador
    }
}