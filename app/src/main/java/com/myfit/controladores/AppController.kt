package com.myfit.controladores

import com.myfit.database.ProveedorServicios
import com.myfit.database.RespuestaJSon
import com.myfit.modelo.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppController {
    lateinit var retrofit: ProveedorServicios
    fun inicializarRetrofit(){
        val url = "http://10.0.2.2:8080/myfit/content/"
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.retrofit= retrofitBuilder.create(ProveedorServicios::class.java)
    }
    suspend fun insertarUsuario(usuario: Usuario){
        retrofit.insertarUsuario(usuario)
    }
    suspend fun getUsuarios(): MutableList<Usuario>? {
        return retrofit.getUsuarios().body()
    }
    suspend fun updateUsuario(usuario: Usuario){
        var r : Response<RespuestaJSon> = retrofit.actualizarUsuario(usuario)
        //Log.i("REST", "status = ${r.code()} body = ${r.body()}")
    }
    suspend fun deleteUsuario(id: Int){
        var r : Response<RespuestaJSon> = retrofit.eliminarUsuario(id)
    }

    suspend fun insertarEjercicio(ejercicio: Ejercicio){
        retrofit.insertarEjercicio(ejercicio)
    }
    suspend fun getEjercicios(): MutableList<Ejercicio>? {
        return retrofit.getEjercicios().body()
    }
    suspend fun updateEjercicio(ejercicio: Ejercicio){
        var r : Response<RespuestaJSon> = retrofit.actualizarEjercicio(ejercicio)
        //Log.i("REST", "status = ${r.code()} body = ${r.body()}")
    }
    suspend fun deleteEjercicio(id: Int){
        var r : Response<RespuestaJSon> = retrofit.eliminarEjercicio(id)
    }
    suspend fun getEjercicioGrupoMuscular() : MutableList<EjercicioGrupoMuscular>? {
        return retrofit.getEjercicioGrupoMuscular().body()
    }
    suspend fun getValoracionEjercicio(id: Int): Double? {
        return retrofit.getValoracionFromIdEjercicio(id).body()?.get("media_valoraciones")?.asDouble
    }
    suspend fun postValoracion(usuarioValoraEjercicio: UsuarioValoraEjercicio){
        retrofit.postValoracionEjercicio(usuarioValoraEjercicio)
    }

    suspend fun findRutinasFromUser(id : Int) : MutableList<Rutina>?{
        return retrofit.getRutinasFromUser(id).body()
    }
    suspend fun insertarRutina(rutina: Rutina){
        retrofit.insertarRutina(rutina)
    }
    suspend fun getRutinas(): MutableList<Rutina>? {
        return retrofit.getRutinas().body()
    }
    suspend fun updateRutina(rutina: Rutina){
        var r : Response<RespuestaJSon> = retrofit.actualizarRutina(rutina)
        //Log.i("REST", "status = ${r.code()} body = ${r.body()}")
    }
    suspend fun deleteRutina(id: Int){
        var r : Response<RespuestaJSon> = retrofit.eliminarRutina(id)
    }
}