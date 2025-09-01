package com.example.listaelementos

import com.example.listaelementos.database.dao.ProdutoDao
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.repositories.toEntity
import com.example.listaelementos.repositories.toProduto
import com.example.listaelementos.ui.viewmodels.MainViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var repository : ProdutoRepository
    private lateinit var viewModel : MainViewModel

    @Before
    fun setup(){
        repository = mockk<ProdutoRepository>()
        viewModel = MainViewModel(repository)
    }

    @Test
    fun deveCalcularTotalCorretamenteERetornarEmStringQuandoUpdateTotalForChamado() {
        val produtos = listOf<Produto>()

        assert(viewModel.updateTotal(produtos) == "R\$ 0,00")
    }

}