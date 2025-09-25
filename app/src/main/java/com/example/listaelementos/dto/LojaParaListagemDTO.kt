package com.example.listaelementos.dto

import com.example.listaelementos.domain.models.Loja
import java.math.BigDecimal

data class LojaParaListagemDTO(
    val name : String,
    val image: String?,
    val distance: BigDecimal
)

fun Loja.paraLojaParaListagemDTO(): LojaParaListagemDTO {
    return LojaParaListagemDTO(
        name = this.name,
        image = this.image,
        distance = this.distance
    )
}