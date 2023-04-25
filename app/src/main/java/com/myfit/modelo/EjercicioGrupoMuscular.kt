package com.myfit.modelo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class EjercicioGrupoMuscular(var idEjercicio:Int,var nombreGrupoMuscular:String) : Parcelable {
}