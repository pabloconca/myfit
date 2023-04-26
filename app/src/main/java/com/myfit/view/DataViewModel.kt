package com.myfit.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myfit.modelo.Ejercicio
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
    private val ejercicioRutina= MutableLiveData<EjercicioRutina?>()
    val getEjercicioRutina: MutableLiveData<EjercicioRutina?> get() = ejercicioRutina
    fun setEjercicioRutina(item: EjercicioRutina?) {
        ejercicioRutina.value = item
    }
    private val ejercicioRutinaEditado= MutableLiveData<EjercicioRutina?>()
    val getEjercicioRutinaEditado: MutableLiveData<EjercicioRutina?> get() = ejercicioRutinaEditado
    fun setEjercicioRutinaEditado(item: EjercicioRutina?) {
        ejercicioRutinaEditado.value = item
    }
    private val ejercicioRutinaAdd= MutableLiveData<EjercicioRutina?>()
    val getEjercicioRutinaAdd: MutableLiveData<EjercicioRutina?> get() = ejercicioRutinaAdd
    fun setEjercicioRutinaAdd(item: EjercicioRutina?) {
        ejercicioRutinaAdd.value = item
    }
    private val ejercicioRutinaBorrarLista= MutableLiveData<EjercicioRutina?>()
    val getEjercicioRutinaBorrarLista: MutableLiveData<EjercicioRutina?> get() = ejercicioRutinaBorrarLista
    fun setEjercicioRutinaBorrarLista(item: EjercicioRutina?) {
        ejercicioRutinaBorrarLista.value = item
    }
    private val ejercicio= MutableLiveData<Ejercicio>()
    val getEjercicio: LiveData<Ejercicio> get() = ejercicio
    fun setEjercicio(item: Ejercicio) {
        ejercicio.value = item
    }
    private val ejercicioDetalle= MutableLiveData<Ejercicio>()
    val getEjercicioDetalle: LiveData<Ejercicio> get() = ejercicioDetalle
    fun setEjercicioDetalle(item: Ejercicio) {
        ejercicioDetalle.value = item
    }
}