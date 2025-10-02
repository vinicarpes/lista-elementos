package com.example.listaelementos.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.getOrAwaitValue
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.ui.viewmodels.CadastroViewModel
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CadastroViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val repo = mockk<ProdutoRepository>()
    private val viewModel = CadastroViewModel(repo)

    @Test
    fun `deve chamar metodo salvar do repositorio e atualizar o livedata quando salvar um produto valido`() =
        runTest {
            val p = Produto("arroz", 10.0, 10, null)

            coEvery { repo.salvar(p) }.returns(Result.success(Unit))

            viewModel.salvar(p)

            coVerify { repo.salvar(p) }

            val produtoFoiSalvo = viewModel.salvoComSucesso.getOrAwaitValue()

            produtoFoiSalvo shouldBe true
        }

    @Test
    fun `deve retornar falso quando receber campos vazios em check fields`() {
        val p = Produto("", 1.0, 10, null)
        val camposPreenchidosCorretamente = viewModel.verificaCampos(p.nome, p.valor.toString(), p.quantidade.toString())

        camposPreenchidosCorretamente shouldBe false
    }

    @Test
    fun `deve retornar true quando receber campos nao vazios no metodo verificaCampos`() {
        val p = Produto("arroz", 10.0, 10, null)
        val camposPreenchidosCorretamente = viewModel.verificaCampos(p.nome, p.valor.toString(), p.quantidade.toString())

        camposPreenchidosCorretamente shouldBe true
    }

    @Test
    fun `deve chamar metodo salvar do repo quando verificar produto valido`() {
        val p = Produto("arroz", 10.0, 10, null)

        coEvery { repo.salvar(p) }.returns(Result.success(Unit))
        viewModel.valiadaParaSalvarProduct(p)

        coVerify { repo.salvar(p) }
    }
}