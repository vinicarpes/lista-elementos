package com.example.listaelementos.repositories

import com.example.listaelementos.dto.RemoteLojasDTO
import com.example.listaelementos.retrofit.service.ApiService
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LojaRepositoryTest {

    private val apiService = mockk<ApiService>()
    private val repo = LojaRepository(apiService)

    @Test
    fun `deve chamar api quando buscar dados`() = runTest {
        val retornoEsperado = RemoteLojasDTO(1, listOf())
        coEvery {
            apiService.getLojas()
        } returns retornoEsperado

        val resultado = repo.buscarDados()

        coVerify {
            apiService.getLojas()
        }
        retornoEsperado shouldBe resultado
    }
    @Test
    fun `deve retornar null quando api lancar excecao`() = runTest {
        coEvery { apiService.getLojas() } throws RuntimeException()

        val result = repo.buscarDados()

        coVerify(exactly = 1) { apiService.getLojas() }
        result shouldBe null
    }
}