package com.myfit.view

import android.content.Intent
import android.media.Rating
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.myfit.R
import com.myfit.controladores.AppController
import com.myfit.modelo.Ejercicio
import com.myfit.modelo.UsuarioValoraEjercicio
import com.myfit.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FragmentDetalleEjercicio : Fragment() {
    private val model:DataViewModel by activityViewModels()
    lateinit var ejercicio: Ejercicio
    var listaGruposMusculares = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_detalle_ejercicio,container,false)
        var valoracion = 0.0
        val updateObserver = Observer<Ejercicio> {
            ejercicio = it
            CoroutineScope(Dispatchers.IO).launch {
                AppController.getEjercicioGrupoMuscular()?.forEach {ejGrupoMuscular ->
                    if(ejGrupoMuscular.idEjercicio == ejercicio.id){
                        listaGruposMusculares.add(ejGrupoMuscular.nombreGrupoMuscular)
                    }
                }
                valoracion = AppController.getValoracionEjercicio(ejercicio.id)!!
                withContext(Dispatchers.Main){
                    var gruposMusculares = StringBuilder()
                    listaGruposMusculares.forEach {
                        gruposMusculares.append(it)
                    }
                    view.findViewById<TextView>(R.id.grupoEjercicioDetalle).text = gruposMusculares.toString()
                    view.findViewById<TextView>(R.id.valoracion).text = valoracion.toString()
                    view.findViewById<RatingBar>(R.id.ratingBar).rating = valoracion.toFloat()

                }
            }
        }
        model.getEjercicioDetalle.observe(requireActivity(),updateObserver)
        view.findViewById<TextView>(R.id.nombreEjercicioDetalle).text = ejercicio.nombre
        view.findViewById<TextView>(R.id.descripcionEjercicioDetalle).text = ejercicio.descripcion
        var link = view.findViewById<TextView>(R.id.ejemploEjercicioDetalle)
        link.text = "enlace"
        var video = ejercicio.ejemplo
        link.setOnClickListener{
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(video)
                )
            )
        }
        var ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                var usuarioValoraEjercicio = UsuarioValoraEjercicio(Utils.usuarioActual,ejercicio,rating.toDouble())
                AppController.postValoracion(usuarioValoraEjercicio)
                valoracion = AppController.getValoracionEjercicio(ejercicio.id)!!
                withContext(Dispatchers.Main){
                    view.findViewById<TextView>(R.id.valoracion).text = valoracion.toString()
                    Toast.makeText(requireContext(),"Valoración añadida correctamente",Toast.LENGTH_SHORT).show()
                }
            }
        }
        view.findViewById<TextView>(R.id.tipoEjercicioDetalle).text = ejercicio.tipo


        return view
    }
}