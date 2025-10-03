package com.example.listaelementos.usecases

import com.example.listaelementos.dto.LojaParaListagemUi
import com.example.listaelementos.dto.RemoteLojasDTO
import com.example.listaelementos.repositories.LojaRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LojaUseCaseTest {
    private val repo = mockk<LojaRepository>()
    private val useCase = LojaUseCase(repo)

    @Test
    fun `deve converter DTO para lista de lojas corretamente`() {
        val dto = RemoteLojasDTO(1, listOf())

        val resultado = useCase.dtoParaListaLojas(dto)

        resultado shouldNotBe null
    }

    @Test
    fun `deve retornar lista vazia ao converter dto nulo para lista de lojas`(){
        val dto = null

        val resultado = useCase.dtoParaListaLojas(dto)

        resultado shouldBe emptyList()
    }

    @Test
    fun `deve chamar repository ao buscar lojas e retornar lista de lojas para listagem`()= runTest {
        val retornoEsperado = listOf<LojaParaListagemUi>()

        coEvery { repo.buscarDados() } returns RemoteLojasDTO(1, listOf())

        val resultado = useCase.buscarLojas()

        coVerify { repo.buscarDados() }

        resultado shouldBe Result.success(retornoEsperado)
    }
}