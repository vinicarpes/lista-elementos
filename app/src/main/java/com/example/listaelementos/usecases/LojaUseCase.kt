package com.example.listaelementos.usecases

import com.example.listaelementos.dto.LojaParaListagemUi
import com.example.listaelementos.dto.RemoteLojasDTO
import com.example.listaelementos.dto.paraLojaParaListagemDTO
import com.example.listaelementos.repositories.LojaRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class LojaUseCase(private val repository: LojaRepository) {
    suspend fun buscarLojas(): Result<List<LojaParaListagemUi>> = withContext(IO) {
        runCatching {
            dtoParaListaLojas(
                repository.buscarDados())
        }
    }

    fun dtoParaListaLojas(dto: RemoteLojasDTO?): List<LojaParaListagemUi> {
        val listaLojas = mutableListOf<LojaParaListagemUi>()
        dto?.data.let {
            it?.map { loja ->
                val lojaParaListagem = loja.paraLojaParaListagemDTO()
                listaLojas.add(lojaParaListagem)
            }
        }
        return listaLojas
    }
}