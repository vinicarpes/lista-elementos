package com.example.listaelementos.ui.viewmodels

import com.example.listaelementos.R.string.erro_buscar_lojas
import com.example.listaelementos.R.string.lojas_nao_encontradas
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listaelementos.dto.LojaParaListagemUi
import com.example.listaelementos.usecases.LojaUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListaLojasComposeViewModel(
    private val useCase: LojaUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _lojaState = MutableStateFlow<LojaState>(LojaState.Loading)
    val lojaState = _lojaState.asStateFlow()

    fun getLojas() {
        viewModelScope.launch(dispatcher) {
            _lojaState.update { LojaState.Loading }
            val result = useCase.buscarLojas()
            result.onSuccess { lojas ->
                atualizarStateComLojas(lojas)
            }.onFailure { e ->
                atualizarStateComErro()
            }
        }
    }

    private fun atualizarStateComErro() {
        val mensagemErro = erro_buscar_lojas.toString()
        _lojaState.update { LojaState.Error(mensagemErro) }
    }

    private fun atualizarStateComLojas(lojas: List<LojaParaListagemUi>) {
        if (!lojas.isEmpty()) {
            _lojaState.update {
                LojaState.Success(lojas)
            }
        } else _lojaState.update { LojaState.Vazio(lojas_nao_encontradas.toString()) }
    }
}


sealed class LojaState {
    object Loading : LojaState()
    data class Success(val lojas: List<LojaParaListagemUi>) : LojaState()
    data class Error(val message: String) : LojaState()
    data class Vazio(val message: String) : LojaState()
}
