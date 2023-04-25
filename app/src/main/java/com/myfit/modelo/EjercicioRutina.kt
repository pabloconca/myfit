package com.myfit.modelo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class EjercicioRutina(var id:Int, var ejercicio:Ejercicio,var ejercicioRutinaPK: EjercicioRutinaPK, var kilometrosRecorridos:Double, var minutos:Double, var repeticionesEjercicio:Int,
                        var seriesEjercicio:Int, var valoracion:Int) : Parcelable {
}