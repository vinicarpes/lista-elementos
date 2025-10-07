package com.example.listaelementos.dto

import com.example.listaelementos.domain.models.Loja
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class LojaParaListagemUi(
    val name : String,
    val image: String?,
    val distance: BigDecimal
)

fun Loja.paraLojaParaListagemDTO(): LojaParaListagemUi {
    return LojaParaListagemUi(
        name = this.name,
        image = this.image,
        distance = this.distance
    )
}