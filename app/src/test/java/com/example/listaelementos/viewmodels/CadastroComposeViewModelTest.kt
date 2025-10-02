package com.example.listaelementos.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.ui.viewmodels.CadastroComposeViewModel
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import com.example.listaelementos.R.string.*

class CadastroComposeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val repo = mockk<ProdutoRepository>()
    private val viewModel = CadastroComposeViewModel(repo)

    @Test
    fun `deve atualizar mensagem de sucesso quando salvar um produto valido`() = runTest {
        val produtoValido = Produto("arroz", 10.0, 10, null)

        coEvery { repo.salvar(produtoValido) }.returns(Result.success(Unit))

        viewModel.salvar(produtoValido)

        coVerify { repo.salvar(produtoValido) }

        val mensagemSucesso = viewModel.state.value.mensagemSucesso

        mensagemSucesso shouldBe produto_salvo_com_sucesso.toString()
    }

    @Test
    fun `deve atualizar mensagem de erro quando salvar um produto invalido`() = runTest {
        val produtoInvalido = Produto("", 0.0, 10, null)

        coEvery { repo.salvar(produtoInvalido) }.returns(Result.failure(RuntimeException()))

        viewModel.salvar(produtoInvalido)

        coVerify { repo.salvar(produtoInvalido) }

        val mensagemErro = viewModel.state.value.mensagemErro

        mensagemErro shouldBe produto_nao_foi_salvo.toString()
    }

    @Test
    fun `deve atualizar mensagem de erro quando atualizar um produto invalido`() = runTest {
        val produtoInvalido = Produto("Banana", 0.0, 10, null, 9)

        coEvery { repo.atualizar(produtoInvalido, 9) }.returns(Result.failure(RuntimeException()))

        viewModel.atualizar(produtoInvalido, 9)

        coVerify { repo.atualizar(produtoInvalido, 9) }

        val mensagemErro = viewModel.state.value.mensagemErro

        mensagemErro shouldBe produto_nao_foi_atualizado.toString()
    }

    @Test
    fun `deve atualizar mensagem de sucesso quando atualizar um produto valido`() = runTest {
        val produtoValido = Produto("Banana", 3.99, 10, null, 9)

        coEvery { repo.atualizar(produtoValido, 9) }.returns(Result.success(Unit))

        viewModel.atualizar(produtoValido, 9)

        coVerify { repo.atualizar(produtoValido, 9) }

        val mensagemSucesso = viewModel.state.value.mensagemSucesso

        mensagemSucesso shouldBe produto_atualizado_com_sucesso.toString()
    }

    @Test
    fun `deve retornar true quando receber campos nao vazios no metodo verificaCampos`() {
        val p = Produto("arroz", 10.0, 10, null)
        val camposPreenchidosCorretamente =
            viewModel.verificaCampos(p.nome, p.valor.toString(), p.quantidade.toString())

        camposPreenchidosCorretamente shouldBe true
    }
    
    @Test
    fun `deve retornar false quando receber campos invalidos no metodo verificaCampos`() {
        val p = Produto("", 0.0, 10, null)
        val camposPreenchidosIncorretamente =
            viewModel.verificaCampos(p.nome, p.valor.toString(), p.quantidade.toString())

        camposPreenchidosIncorretamente shouldBe false
    }

}