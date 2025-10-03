package com.example.listaelementos.viewmodels

import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.ui.viewmodels.MainViewModel
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.Test

class MainViewModelTest {

    private val repository = mockk<ProdutoRepository>()
    private val viewModel = MainViewModel(repository)

    @Test
    fun `deve calcular total corretamente e retornar em String quando atualizarValorTotal for chamado`() {
        val produtos = emptyList<Produto>()
        val totalCalculado = viewModel.atualizarValorTotal(produtos)

        totalCalculado shouldBe "R$\u00A00,00"
    }

}