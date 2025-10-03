package com.example.listaelementos.viewmodels

import com.example.listaelementos.dto.LojaParaListagemUi
import com.example.listaelementos.R.string.lojas_nao_encontradas
import com.example.listaelementos.R.string.erro_buscar_lojas
import com.example.listaelementos.ui.viewmodels.ListaLojasComposeViewModel
import com.example.listaelementos.ui.viewmodels.LojaState
import com.example.listaelementos.usecases.LojaUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.math.BigDecimal

class ListaLojasComposeViewModelTest {

    private val useCase = mockk<LojaUseCase>()

    private val viewModel = ListaLojasComposeViewModel(useCase)

    @Test
    fun `deve chamar useCase ao buscar lojas`() = runTest {
        coEvery { useCase.buscarLojas() } returns Result.success(emptyList())

        viewModel.getLojas()

        coVerify(exactly = 1) { useCase.buscarLojas() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `deve atualizar state com lojas quando buscar lista de lojas nao vazia com sucesso`() =
        runTest {
            val lojasMock = listOf(
                LojaParaListagemUi(
                    "Nome Bacana", "Link bacana",
                    BigDecimal(12)
                )
            )
            coEvery { useCase.buscarLojas() } returns Result.success(lojasMock)

            val dispatcher = StandardTestDispatcher(testScheduler)
            val viewModel = ListaLojasComposeViewModel(useCase, dispatcher)

            viewModel.getLojas()

            testScheduler.advanceUntilIdle()

            val state = viewModel.lojaState.value
            state shouldBe LojaState.Success(lojasMock)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `deve atualizar state como vazio quando buscar lojas e retornar lista vazia`() = runTest {
        val lojasMock = listOf<LojaParaListagemUi>()
        coEvery { useCase.buscarLojas() } returns Result.success(lojasMock)

        val dispatcher = StandardTestDispatcher(testScheduler)
        val viewModel = ListaLojasComposeViewModel(useCase, dispatcher)

        viewModel.getLojas()

        testScheduler.advanceUntilIdle()

        val state = viewModel.lojaState.value
        state shouldBe LojaState.Vazio(lojas_nao_encontradas.toString())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `deve atualizar state como erro quando buscar lojas e lancar excecao`() = runTest {
        coEvery { useCase.buscarLojas() } returns Result.failure(RuntimeException())

        val dispatcher = StandardTestDispatcher(testScheduler)
        val viewModel = ListaLojasComposeViewModel(useCase, dispatcher)

        viewModel.getLojas()

        testScheduler.advanceUntilIdle()

        val state = viewModel.lojaState.value
        state shouldBe LojaState.Error(erro_buscar_lojas.toString())
    }
}