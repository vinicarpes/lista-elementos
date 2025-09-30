package com.example.listaelementos.ui.viewmodels

import com.example.listaelementos.R.string.erro_buscar_lojas
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listaelementos.dto.LojaParaListagemUi
import com.example.listaelementos.usecases.LojaUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListaLojasComposeViewModel(
    private val useCase: LojaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProdutoComposeState())
    val state = _state.asStateFlow()

    private val _lojaState = MutableStateFlow(LojaState())
    val lojaState = _lojaState.asStateFlow()

    fun getLojas() {
        viewModelScope.launch(Dispatchers.IO) {
            _lojaState.update { LojaState.Loading }
            val result = useCase.buscarLojas()
            result.onSuccess { lojas ->
                if (!lojas.isEmpty()){
                    _lojaState.update {
                        LojaState.Success(lojas)
                    }
                    Log.d("LojasState.Success", "Lojas: $lojas")
                }else _lojaState.update { LojaState.Vazio("Não foi possível encontrar nenhuma loja :(") }
            }.onFailure { e ->
                val mensagemErro = erro_buscar_lojas.toString()
                Log.d("LojasState.Error", result.toString())
                _lojaState.update { LojaState.Error(mensagemErro) }
            }
        }
    }
}




    open class LojaState {
        object Loading : LojaState()
        data class Success(val lojas: List<LojaParaListagemUi>) : LojaState()
        data class Error(val message: String) : LojaState()
        data class Vazio(val message: String) : LojaState()
    }
