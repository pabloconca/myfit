package com.myfit.modelo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class EjercicioRutinaPK(var idEjercicio : Int, var idRutina : Int) : Parcelable {
}