package com.example.listaelementos.usecases

import com.example.listaelementos.dto.LojaParaListagemDTO
import com.example.listaelementos.dto.LojasDTO
import com.example.listaelementos.dto.paraLojaParaListagemDTO
import com.example.listaelementos.repositories.LojaRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class LojaUseCase(private val repository: LojaRepository) {
    suspend fun buscarLojas(): Result<List<LojaParaListagemDTO>> = withContext(IO) {
        runCatching {
            dtoParaListaLojas(
                repository.getLojas())
        }
    }

    fun dtoParaListaLojas(dto: LojasDTO?): List<LojaParaListagemDTO> {
        val listaLojas = mutableListOf<LojaParaListagemDTO>()
        dto?.data.let {
            it?.map { loja ->
                val lojaParaListagem = loja.paraLojaParaListagemDTO()
                listaLojas.add(lojaParaListagem)
            }
        }
        return listaLojas
    }
}