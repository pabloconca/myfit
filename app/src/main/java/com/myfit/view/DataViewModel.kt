package com.myfit.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myfit.modelo.EjercicioRutina

class DataViewModel : ViewModel() {
    private val tieneQueActualizarRutinas= MutableLiveData<Boolean>()
    val getTieneQueActualizarRutinas: LiveData<Boolean> get() = tieneQueActualizarRutinas
    fun setTieneQueActualizarRutinas(item: Boolean) {
        tieneQueActualizarRutinas.value = item
    }
    private val liveData= MutableLiveData<Boolean>()
    val getTieneQueActualizarUser: LiveData<Boolean> get() = liveData
    fun setTieneQueActualizarUser(item: Boolean) {
        liveData.value = item
    }
    private val ejercicioRutina= MutableLiveData<EjercicioRutina>()
    val getEjercicioRutina: LiveData<EjercicioRutina> get() = ejercicioRutina
    fun setEjercicioRutina(item: EjercicioRutina) {
        ejercicioRutina.value = item
    }
}