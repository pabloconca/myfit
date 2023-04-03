package com.myfit.modelo

class Ejercicio(var id:Int, var nombre:String, var descripcion:String, var ejemplo:String, var tipo:String,
                var ejercicioTieneGrupoMuscularCollection:List<EjercicioGrupoMuscular>, var valoracion:Double) {
}