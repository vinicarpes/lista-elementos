package com.example.listaelementos.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.repositories.toProduto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class MainViewModel(private val repository: ProdutoRepository) : ViewModel() {
//    private val _produtos = MutableLiveData<List<Produto>>()
//    val produtos : LiveData<List<Produto>> = _produtos

    private val _state = MutableLiveData<ProdutoState>()
    val state: LiveData<ProdutoState> = _state

    fun getProdutos(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(ProdutoState.Loading)
            val result = repository.getAll()
            result.onSuccess { entities ->
                val produtos = entities.map { entity -> entity.toProduto() }
                _state.postValue(ProdutoState.Success(produtos))
            }.onFailure { e->
                _state.postValue(ProdutoState.Error(e.message ?: "Erro ao buscar produtos"))
            }
        }
    }

    fun updateTotal(produtos: List<Produto>) : String {
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