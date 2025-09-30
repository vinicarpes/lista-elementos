package com.example.listaelementos.ui.viewmodels

import android.util.Log
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
                atualizarMensagemSucesso("Produto atualizado com sucesso!")
            }.onFailure {
                atualizarMensagemErro("Não foi possível atualizar o produto na lista")
            }

        }
    }

    fun atualizar(prod: Produto, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultado = repository.atualizar(prod, id)
            resultado.onSuccess {
                atualizarMensagemSucesso("Produto atualizado com sucesso!")
            }.onFailure {
                atualizarMensagemErro("Não foi possível atualizar o produto na lista")
            }

        }
    }

    fun verificaCampos(nome: String, valor: String, qtd: String): Boolean {
        return nome.isNotEmpty() && valor.isNotEmpty() && qtd.isNotEmpty()
    }


    fun valiadaParaSalvarProduct(
        sucesso: (Boolean) -> Unit
    ) {
        try {
            val p = Produto(
                _state.value.nome,
                _state.value.valor.toDouble(),
                _state.value.quantidade.toInt()
            )
            val camposPreenchidosCorretamente = verificaCampos(p.nome, p.valor.toString(), p.quantidade.toString()) && p.validaProduto()
            if (camposPreenchidosCorretamente) {
                when (_state.value.id != 0) {
                    true -> atualizar(p, _state.value.id)
                    false -> salvar(p)
                }
                sucesso(true)
            } else {
                sucesso(false)
            }
        } catch (e : NumberFormatException){
            atualizarMensagemErro("Preencha todos os campos corretamente")
            Log.d("CadatroComposeViewModel", e.message.toString())
        }

    }

    fun atualizarMensagemErro(mensagem: String) {
        _state.update { it.copy(mensagemErro = mensagem) }
    }
    fun atualizarMensagemSucesso(mensagem: String) {
        _state.update { it.copy(mensagemSucesso = mensagem) }
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

    fun limparMensagem() {
        _state.update { it.copy(mensagemSucesso = null, mensagemErro = null) }
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