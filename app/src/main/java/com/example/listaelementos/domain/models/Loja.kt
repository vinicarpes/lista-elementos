package com.example.listaelementos.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Loja(
    val uuid: String,
    val name: String,
    val slug: String,
    val image: String,
    val phone: String,
    @SerialName("is_online")
    val isOnline: Boolean,
    @SerialName("delivery_time")
    val deliveryTime: Int,
    @SerialName("cooking_time")
    val cookingTime: Int,
    @SerialName("can_pickup")
    val canPickup: Boolean,
    val location: String,
    @SerialName("minimum_order_price")
    val minimumOrderPrice: Int,
    val description: String,
    val categories: List<Categoria>,
    @SerialName("payment_methods")
    val paymentMethods: List<MetodoPagamento>,
    val address: Endereco,
    @SerialName("company_type")
    val companyType: String,
    @SerialName("formatted_address")
    val formattedAddress: String,
    val cityId: Int,
    val distance: BigDecimal,
    val new: Boolean
)