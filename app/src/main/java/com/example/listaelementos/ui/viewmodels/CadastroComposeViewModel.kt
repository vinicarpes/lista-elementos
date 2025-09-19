package com.example.listaelementos.ui.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.copy
import androidx.compose.runtime.LaunchedEffect
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

    fun salvar(prod: Produto) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultado = repository.salvar(prod)
            resultado.onSuccess {
                _state.update { it.copy(mensagemSucesso = "Produto inserido com sucesso!") }
            }.onFailure {
                _state.update { it.copy(mensagemErro = "Não foi possível inserir o produto na lista") }
            }

        }
    }

    fun atualizar(prod: Produto, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultado = repository.atualizar(prod, id)
            resultado.onSuccess {
                _state.update { it.copy(mensagemSucesso = "Produto atualizado com sucesso!") }
            }.onFailure {
                _state.update { it.copy(mensagemErro = "Não foi possível atualizar o produto na lista") }
            }

        }
    }

    fun verificaCampos(nome: String, valor: String, qtd: String): Boolean {
        return nome.isNotEmpty() && valor.isNotEmpty() && qtd.isNotEmpty()
    }


    fun valiadaParaSalvarProduct() : String {
        val mensagemRetornada : String?
        val p = Produto(
            _state.value.nome,
            _state.value.valor.toDouble(),
            _state.value.quantidade.toInt()
        )
        val camposPreenchidosCorretamente =
            verificaCampos(p.nome, p.valor.toString(), p.quantidade.toString()) && p.validaProduto()
        if (camposPreenchidosCorretamente) {
            when (_state.value.id != 0) {
                true -> atualizar(p, _state.value.id)
                false -> salvar(p)
            }
            mensagemRetornada = _state.value.mensagemSucesso
            return mensagemRetornada.toString()
        } else {
            mensagemRetornada = _state.value.mensagemErro
            return mensagemRetornada.toString()
        }
    }

    fun aoMudarNome(novoNome: String) {
        _state.update { it.copy(nome = novoNome) }
    }

    fun aoMudarQuantidade(novaQuantidade: String?) {
        _state.update { it.copy(quantidade = novaQuantidade.toString()) }
    }

    fun aoMudarValor(novoValor: String?) {
        _state.update { it.copy(valor = novoValor.toString()) }

    }

    fun aoMudarId(id: Int) {
        _state.update { it.copy(id = id) }
    }

    fun atualizaValoresProdutoState(p: Produto?) {
        aoMudarNome(p?.nome ?: "")
        aoMudarQuantidade(p?.quantidade?.toString() ?: "")
        aoMudarValor(p?.valor?.toString() ?: "")
        aoMudarId(p?.id ?: 0)
    }
}

data class ProdutoFormState(
    val nome: String = "",
    val quantidade: String = "",
    val valor: String = "",
    val id: Int = 0,
    val mensagemErro: String? = null,
    val mensagemSucesso: String? = null
)