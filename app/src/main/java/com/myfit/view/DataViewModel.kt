package com.myfit.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
}