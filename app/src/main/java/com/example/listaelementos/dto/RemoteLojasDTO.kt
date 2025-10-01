package com.example.listaelementos.dto

import com.example.listaelementos.domain.models.Loja
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteLojasDTO(
    @SerialName("total_count")
    val totalCount: Int,
    val data: List<Loja>
)