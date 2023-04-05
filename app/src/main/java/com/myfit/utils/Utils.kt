package com.myfit.utils

import com.myfit.modelo.Usuario
import java.security.MessageDigest

object Utils {
    var estaLogeado = false
    lateinit var usuario:Usuario
     fun setUser(usuarioNuevo: Usuario){
        usuario = usuarioNuevo
     }
     fun getUser() : Usuario{
        return usuario
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
}