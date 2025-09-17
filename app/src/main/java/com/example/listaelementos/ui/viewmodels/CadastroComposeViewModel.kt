package com.example.listaelementos.ui.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.copy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CadastroComposeViewModel(private val repository: ProdutoRepository) : ViewModel() {

    private val _state = MutableStateFlow(ProdutoFormState())
    val state = _state.asStateFlow()

    private val _salvoComSucesso = MutableLiveData<Boolean>()
    val salvoComSucesso: LiveData<Boolean> = _salvoComSucesso

    fun salvar(prod: Produto) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultado = repository.salvar(prod)
            resultado.onSuccess {
                _salvoComSucesso.postValue(true)
            }.onFailure {
            }

        }
    }

    fun verificaCampos(nome: String, valor: String, qtd: String): Boolean {
        return nome.isNotEmpty() && valor.isNotEmpty() && qtd.isNotEmpty()
    }

    fun valiadaParaSalvarProduct(nome: String, valor: String, qtd: String) {
        try {
            val p = Produto(nome, valor.toDouble(), qtd.toInt())
            val camposPreenchidosCorretamente = verificaCampos(
                p.nome,
                p.valor.toString(),
                p.quantidade.toString()
            ) && p.validaProduto()
            if (camposPreenchidosCorretamente) {
                salvar(p)
            }

        } catch (e: IllegalArgumentException) {
            Log.e("CadastroProduto", "Erro de validação: ${e.message}", e)
        }
    }

    fun aoMudarNome(novoNome: String) {
        _state.update { it.copy(nome = novoNome) }
    }

    fun aoMudarQuantidade(novaQuantidade: String) {
        _state.update { it.copy(quantidade = novaQuantidade) }
    }

    fun aoMudarValor(novoValor: String) {
        _state.update { it.copy(valor = novoValor) }
    }

}

data class ProdutoFormState(
    val nome: String = "",
    var quantidade: String = "",
    var valor: String = ""
)