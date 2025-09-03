package com.example.listaelementos.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CadastroViewModel(private val repository: ProdutoRepository) : ViewModel() {

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage // essa variável pode ser acessada pela activity

    private val _saveSuccessEvent = MutableLiveData<Boolean>()
    val saveSuccessEvent: LiveData<Boolean> = _saveSuccessEvent

    fun save(prod: Produto) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.save(prod)
            result.onSuccess {
                _saveSuccessEvent.postValue(true)
            }.onFailure {
                _toastMessage.postValue("Erro ao salvar o produto")
            }

        }
    }

    fun checkFields(nome: String, valor: String, qtd: String): Boolean {
        return nome.isNotEmpty() && valor.isNotEmpty() && qtd.isNotEmpty()
    }

    fun valiadateToSaveProduct(p: Produto){
        if (checkFields(p.nome, p.valor.toString(), p.quantidade.toString()) && p.validaProduto()) {
            save(p)
        } else {
            _toastMessage.value = "Preencha todos os campos corretamente"
        }
    }

    fun onToastShown() {
        _toastMessage.value = null

    }

}