package com.myfit.modelo

import android.os.Parcel
import android.os.Parcelable

class Rutina(var id: Int, var idUsuario: Usuario, var nombre: String, var ejercicioRutinaCollection: List<EjercicioRutina>) :
    Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable<Usuario>(Usuario::class.java.classLoader)!!,
        parcel.readString()!!,
        mutableListOf<EjercicioRutina>().apply {
            parcel.readList(this, EjercicioRutina::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(idUsuario, flags)
        parcel.writeString(nombre)
        parcel.writeList(ejercicioRutinaCollection)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rutina> {
        override fun createFromParcel(parcel: Parcel): Rutina {
            return Rutina(parcel)
        }

        override fun newArray(size: Int): Array<Rutina?> {
            return arrayOfNulls(size)
        }
    }
}
