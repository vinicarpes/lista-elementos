package com.example.listaelementos.viewmodels

import com.example.listaelementos.R
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.ui.viewmodels.MainComposeViewModel
import com.example.listaelementos.ui.viewmodels.ProdutoComposeState
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MainComposeViewModelTest {

    private val repository = mockk<ProdutoRepository>()
    private val viewModel = MainComposeViewModel(repository)

    @Test
    fun `deve chamar repository ao buscar produtos`() {
        coEvery { repository.buscarTodos() } returns Result.success(emptyList())

        viewModel.getProdutos()

        coVerify { repository.buscarTodos() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `deve atualizar state com lista de produtos ao buscar produtos com sucesso`() = runTest {
        val produtosMock = listOf(Produto("Nome Bacana", 3.99, 10))
        val repo = mockk<ProdutoRepository>()

        coEvery { repo.buscarTodos() } returns Result.success(produtosMock)

        val dispatcher = StandardTestDispatcher(testScheduler)
        val viewModel = MainComposeViewModel(repo, dispatcher)

        viewModel.getProdutos()
        testScheduler.advanceUntilIdle()

        val state = viewModel.state.value
        state shouldBe state shouldBe ProdutoComposeState.Success(
            produtos = produtosMock,
            valorTotal = "R$ 39,90"
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `deve atualizar state com erro ao buscar produtos com falha`() = runTest {
        val repo = mockk<ProdutoRepository>()

        coEvery { repo.buscarTodos() } returns Result.failure(Throwable())

        val dispatcher = StandardTestDispatcher(testScheduler)
        val viewModel = MainComposeViewModel(repo, dispatcher)

        viewModel.getProdutos()
        testScheduler.advanceUntilIdle()

        val state = viewModel.state.value
        state shouldBe ProdutoComposeState.Error( R.string.erro_buscar_produtos.toString())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `deve atualizar state com lista de produtos ao remover produto com sucesso`() = runTest {
        val repo = mockk<ProdutoRepository>()
        val produto = Produto("Nome Bacana", 3.99, 10, id = 1)

        coEvery { repo.buscarTodos() } returns Result.success(emptyList())
        coEvery { repo.remover(produto) } returns Result.success(Unit)

        val dispatcher = StandardTestDispatcher(testScheduler)
        val viewModel = MainComposeViewModel(repo, dispatcher)

        viewModel.removerProduto(produto)
        testScheduler.advanceUntilIdle()

        val state = viewModel.state.value
        state shouldBe ProdutoComposeState.Success(emptyList(), "R$ 0,00")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `deve atualizar state com erro ao remover produto com falha`() = runTest {
        val repo = mockk<ProdutoRepository>()
        val produto = Produto("Nome Bacana", 3.99, 10, id = 1)

        coEvery { repo.buscarTodos() } returns Result.success(emptyList())
        coEvery { repo.remover(produto) } returns Result.failure(Throwable())

        val dispatcher = StandardTestDispatcher(testScheduler)
        val viewModel = MainComposeViewModel(repo, dispatcher)

        viewModel.removerProduto(produto)
        testScheduler.advanceUntilIdle()

        val state = viewModel.state.value
        state shouldBe ProdutoComposeState.Error(R.string.erro_remover_produto.toString())
    }


}