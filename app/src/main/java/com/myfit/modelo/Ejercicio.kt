package com.myfit.modelo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Ejercicio(var id:Int, var nombre:String, var descripcion:String, var ejemplo:String, var tipo:String) :
    Parcelable {
}