package com.example.listaelementos.database.entities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class ProdutoEntity(
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "valor") val valor: Double,
    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER) val quantidade: Int,
    val foto: ByteArray? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
        get() = field

    constructor(nome: String, valor: Double, quantidade: Int) : this(nome, valor, quantidade, null)

    @get:Ignore
    val fotoBitmap: Bitmap?
        get() = foto?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProdutoEntity

        if (valor != other.valor) return false
        if (quantidade != other.quantidade) return false
        if (id != other.id) return false
        if (nome != other.nome) return false
        if (!foto.contentEquals(other.foto)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = valor.hashCode()
        result = 31 * result + quantidade
        result = 31 * result + id
        result = 31 * result + nome.hashCode()
        result = 31 * result + (foto?.contentHashCode() ?: 0)
        return result
    }
}