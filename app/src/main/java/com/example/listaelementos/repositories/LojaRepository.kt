package com.example.listaelementos.repositories

import com.example.listaelementos.dto.LojasDTO
import com.example.listaelementos.retrofit.service.ApiService

class LojaRepository(
    private val apiService: ApiService
) {

    suspend fun getLojas(): LojasDTO? {
        var lojas: LojasDTO? = null
        runCatching {
            lojas = apiService.getLojas()
        }
        return lojas
    }

}