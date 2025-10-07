package com.example.listaelementos.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Endereco(
    val street: String,
    val city: String,
    val neighborhood: String,
    @SerialName("zip_code")
    val zipCode: String
)