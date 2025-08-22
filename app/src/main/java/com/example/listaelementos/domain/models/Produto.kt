package com.example.listaelementos.domain.models

import android.graphics.Bitmap
import androidx.room.Ignore

data class Produto(
    val nome: String,
    val valor: Double,
    val quantidade : Int,
    @Ignore val foto: Bitmap? = null,
    var id: Int = 0
    )
