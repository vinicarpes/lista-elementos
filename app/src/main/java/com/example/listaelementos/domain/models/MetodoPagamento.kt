package com.example.listaelementos.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MetodoPagamento(
    val id: Int,
    val name: String,
    val type: String
)