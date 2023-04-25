package com.myfit.modelo

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Rutina(var id: Int, var idUsuario: Usuario, var nombre: String, var ejercicioRutinaCollection: List<EjercicioRutina>) :
    Parcelable {

}
