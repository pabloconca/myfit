package com.myfit.database

import com.myfit.modelo.Ejercicio
import com.myfit.modelo.Rutina
import com.myfit.modelo.Usuario
import retrofit2.Response
import retrofit2.http.*

interface ProveedorServicios {
    @GET("usuario")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getUsuarios(): Response<MutableList<Usuario>>
    @POST("usuario")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun insertarUsuario(@Body usuarios: Usuario): Response<RespuestaJSon>
    @PUT("usuario")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun actualizarUsuario(@Body usuarios: Usuario): Response<RespuestaJSon>
    @DELETE("usuario/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun eliminarUsuario(@Path("id") id : Int): Response<RespuestaJSon>

    @GET("rutina")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getRutinas(): Response<MutableList<Rutina>>
    @GET("rutina/usuario/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getRutinasFromUser(@Path("id") id : Int) : Response<MutableList<Rutina>>
    @POST("rutina")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun insertarRutina(@Body rutina: Rutina): Response<RespuestaJSon>
    @PUT("rutina")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun actualizarRutina(@Body rutina: Rutina): Response<RespuestaJSon>
    @DELETE("rutina/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun eliminarRutina(@Path("id") id : Int): Response<RespuestaJSon>

    @GET("ejercicio")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getEjercicios(): Response<MutableList<Ejercicio>>
    @POST("ejercicio")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun insertarEjercicio(@Body ejercicio: Ejercicio): Response<RespuestaJSon>
    @PUT("ejercicio")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun actualizarEjercicio(@Body ejercicio: Ejercicio): Response<RespuestaJSon>
    @DELETE("ejercicio/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun eliminarEjercicio(@Path("id") id : Int): Response<RespuestaJSon>
}