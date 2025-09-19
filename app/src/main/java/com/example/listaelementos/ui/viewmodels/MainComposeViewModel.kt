package com.example.listaelementos.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listaelementos.R
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

    fun getProdutos() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { ProdutoComposeState.Loading }
            val result = repository.buscarProdutos()
            result.onSuccess { entidades ->
                val produtos = entidades.map { entidade -> entidade.paraProduto() }
                _state.update {
                    ProdutoComposeState.Success(
                        produtos,
                        atualizarValorTotal(produtos)
                    )
                }
            }.onFailure { e ->
                val mensagemErro = R.string.erro_buscar_produtos.toString()
                _state.update { ProdutoComposeState.Error(e.message ?: mensagemErro) }
            }
        }
    }

    fun removerProduto(produto: Produto) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("VIEWMODEL", "Tentando remover: $produto")
            val result = repository.remover(produto)
            result.onSuccess {
                getProdutos()
            }
                .onFailure { e ->
                    val mensagemErro = R.string.erro_remover_produto.toString()
                    _state.update { ProdutoComposeState.Error(e.message ?: mensagemErro) }
                }
        }
    }

    private fun atualizarValorTotal(produtos: List<Produto>): String {
        val soma = produtos.sumOf { it.valor * it.quantidade }
        require(soma >= 0)
        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        return f.format(soma)
    }
}

open class ProdutoComposeState {
    object Loading : ProdutoComposeState()
    data class Success(val produtos: List<Produto>, val valorTotal: String = "0.00") :
        ProdutoComposeState()

    data class Error(val message: String) : ProdutoComposeState()

}