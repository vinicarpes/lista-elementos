package com.example.listaelementos.dto

import com.example.listaelementos.domain.models.Loja

data class LojasDTO(
    val total_count: Int,
    val data: List<Loja>
)