package com.example.listaelementos.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listaelementos.database.database
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.repositories.toProduto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(context: Context) : ViewModel() {
    val repository = ProdutoRepository(context.database.produtoDao())
    private val _produtos = MutableLiveData<List<Produto>>()
    val produtos : LiveData<List<Produto>> = _produtos

    fun getProdutos(){
        viewModelScope.launch(Dispatchers.IO) {
            _produtos.postValue(repository.produtos.map { it.toProduto() })
        }
    }
}