package com.example.listaelementos

import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.ui.viewmodels.MainViewModel
import io.mockk.mockk
import org.junit.Test

class MainViewModelTest {

    private val repository = mockk<ProdutoRepository>()
    private val viewModel = MainViewModel(repository)

    @Test
    fun deveCalcularTotalCorretamenteERetornarEmStringQuandoUpdateTotalForChamado() {
        val produtos = listOf<Produto>()

        assert(viewModel.updateTotal(produtos) == "R\$ 0,00")
    }

}