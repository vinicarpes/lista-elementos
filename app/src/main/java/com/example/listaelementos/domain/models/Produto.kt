package com.example.listaelementos.domain.models

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Ignore

data class Produto(
    val nome: String,
    val valor: Double,
    val quantidade: Int,
    @Ignore val foto: Bitmap? = null,
    var id: Int = 0
) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt(),
        null,
        parcel.readInt(),
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeDouble(valor)
        parcel.writeInt(quantidade)
        parcel.writeInt(id)
    }

    companion object CREATOR : Parcelable.Creator<Produto> {
        override fun createFromParcel(parcel: Parcel): Produto {
            return Produto(parcel)
        }


        override fun newArray(size: Int): Array<Produto?> = arrayOfNulls(size)
    }

    fun validaProduto(): Boolean {
        return valor > 0 && quantidade > 0 && nome.isNotEmpty()
    }
}
