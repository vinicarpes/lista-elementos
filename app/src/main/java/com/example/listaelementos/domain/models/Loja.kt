package com.example.listaelementos.domain.models

import java.math.BigDecimal

data class Loja(
    val uuid: String,
    val name: String,
    val slug: String,
    val image: String,
    val phone: String,
    val is_online: Boolean,
    val delivery_time: Int,
    val cooking_time: Int,
    val can_pickup: Boolean,
    val location: String,
    val minimum_order_price: Int,
    val description: String,
    val categories: List<Categoria>,
    val payment_methods: List<MetodoPagamento>,
    val address: Endereco,
    val company_type: String,
    val formatted_address: String,
    val city_id: Int,
    val distance: BigDecimal,
    val new: Boolean
)