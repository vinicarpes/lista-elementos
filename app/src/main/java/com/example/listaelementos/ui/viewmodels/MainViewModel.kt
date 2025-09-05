package com.example.listaelementos.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.repositories.paraProduto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class MainViewModel(private val repository: ProdutoRepository) : ViewModel() {
    private val _state = MutableLiveData<ProdutoState>()
    val state: LiveData<ProdutoState> = _state

    fun getProdutos(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(ProdutoState.Loading)
            val result = repository.buscarProdutos()
            result.onSuccess { entidades ->
                val produtos = entidades.map { entidade -> entidade.paraProduto() }
                _state.postValue(ProdutoState.Success(produtos))
            }.onFailure { e->
                _state.postValue(ProdutoState.Error(e.message ?: "Erro ao buscar produtos"))
            }
        }
    }

    fun atualizarValorTotal(produtos: List<Produto>) : String {
        val soma = produtos.sumOf { it.valor * it.quantidade }
        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        return f.format(soma)
    }
}

sealed class ProdutoState{
    object Loading : ProdutoState()
    data class Success(val produtos: List<Produto>) : ProdutoState()
    data class Error(val message: String) : ProdutoState()

}