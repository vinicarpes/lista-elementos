package com.example.listaelementos.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.repositories.paraProduto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class MainComposeViewModel(private val repository: ProdutoRepository) : ViewModel() {
    private val _state = MutableStateFlow(ProdutoComposeState())
    val state = _state.asStateFlow()

    fun getProdutos(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update{ it.copy() }
            val result = repository.buscarProdutos()
            result.onSuccess { entidades ->
                val produtos = entidades.map { entidade -> entidade.paraProduto() }
                _state.update { it.copy(produtos = produtos, valorTotal = atualizarValorTotal(produtos)) }
            }.onFailure { e->
                _state.update { it.copy() }
            }
        }
    }

    fun atualizarValorTotal(produtos: List<Produto>) : String {
        val soma = produtos.sumOf { it.valor * it.quantidade }
        require(soma >= 0)
        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        return f.format(soma)
    }
}

data class ProdutoComposeState(
    val produtos: List<Produto> = emptyList(),
    val valorTotal: String = "0.00")