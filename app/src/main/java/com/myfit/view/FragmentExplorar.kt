package com.myfit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myfit.R
import com.myfit.adaptadores.AdaptadorRecyclerExplorar
import com.myfit.controladores.AppController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentExplorar : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_explorar,container,false)
        var recycler = view.findViewById<RecyclerView>(R.id.recycler)
        CoroutineScope(Dispatchers.IO).launch {
            var rutinasList = AppController.getRutinas()
            withContext(Dispatchers.Main){
                var adapter = AdaptadorRecyclerExplorar(rutinasList)
                recycler.adapter = adapter
            }
        }

        recycler.layoutManager=
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        return view
    }
}