package com.example.listaelementos

import android.graphics.Bitmap

data class Produto(val nome: String, val valor: Double, val quantidade : Int, val foto: Bitmap? = null)