package com.example.listaelementos.utils

import android.graphics.Bitmap
import com.example.listaelementos.domain.models.Produto
import java.io.ByteArrayOutputStream


val produtosGlobal = mutableListOf<Produto>()

fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}