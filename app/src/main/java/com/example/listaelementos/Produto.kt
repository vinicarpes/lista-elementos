package com.example.listaelementos

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(tableName = "produto")
data class Produto(
    @ColumnInfo(name = "nome")val nome: String,
    @ColumnInfo(name = "valor")val valor: Double,
    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)val quantidade : Int,
    @Ignore val foto: Bitmap? = null
    ){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
        get() = field

    constructor(nome: String, valor: Double, quantidade: Int) : this(nome, valor, quantidade, null)
}
