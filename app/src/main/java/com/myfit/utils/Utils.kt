package com.myfit.utils

import androidx.fragment.app.FragmentManager
import com.myfit.modelo.Usuario
import com.myfit.view.DialogInicioSesion
import java.security.MessageDigest

object Utils {
    var estaLogeado = false
    lateinit var usuarioActual:Usuario
     fun setUser(usuarioNuevo: Usuario){
        usuarioActual = usuarioNuevo
     }
     fun getUser() : Usuario{
        return usuarioActual
     }
     fun compararPass(hashAnterior : String, hashNuevo : String) : Boolean{
        return hashAnterior == hashNuevo
    }
     fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = password.toByteArray(Charsets.UTF_8)
        val hashedBytes = digest.digest(bytes)
        return hashedBytes.joinToString("") {
            "%02x".format(it)
        }
    }
    fun mostrarDialogoInicioSesion(parentFragmentManager: FragmentManager){
        val dialogo= DialogInicioSesion()
        dialogo.show(parentFragmentManager,"DialogoInicioSesion")
    }
}