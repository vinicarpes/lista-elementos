package com.example.listaelementos.repositories

import com.example.listaelementos.dto.RemoteLojasDTO
import com.example.listaelementos.retrofit.service.ApiService

class LojaRepository(
    private val apiService: ApiService
) {

    suspend fun getLojas(): RemoteLojasDTO? {
        var lojas: RemoteLojasDTO? = null
        runCatching {
            lojas = apiService.getLojas()
        }
        return lojas
    }

}