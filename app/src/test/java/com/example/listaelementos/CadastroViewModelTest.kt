package com.example.listaelementos

import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.ui.viewmodels.CadastroViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CadastroViewModelTest {
    @Test
    fun deveChamarSaveDoRepoQuandoForSalvar() {
        val repo = mockk<ProdutoRepository>()
        val viewModel = CadastroViewModel(repo)
        val p = Produto("arroz", 10.0, 10, null)

        coEvery{ repo.save(p)}.returns(Unit)

        viewModel.save(p)

        coVerify { repo.save(p) }
    }

    @Test
    fun deveRetornarFalsoQuandoReceberCamposVaziosEmCheckFields() {
        // veficar se esta chamando os metodos de salvar
        val repo = mockk<ProdutoRepository>()
        val viewModel = CadastroViewModel(repo)
        val p = Produto("", 1.0, 10, null)

        assertFalse(viewModel.checkFields(p.nome, p.valor.toString(), p.quantidade.toString()))
    }

    @Test
    fun deveRetornarTrueQuandoReceberCamposNaoVaziosEmCheckFields() {
        // veficar se esta chamando os metodos de salvar
        val repo = mockk<ProdutoRepository>()
        val viewModel = CadastroViewModel(repo)
        val p = Produto("arroz", 10.0, 10, null)

        assertTrue(viewModel.checkFields(p.nome, p.valor.toString(), p.quantidade.toString()))
    }

    @Test
    fun deveChamarSaveDoRepoQuandoVerificarProdutoValido() {
        val repo = mockk<ProdutoRepository>()
        val viewModel = CadastroViewModel(repo)
        val p = Produto("arroz", 10.0, 10, null)

        coEvery { repo.save(p) } returns Unit

        viewModel.valiadateToSaveProduct(p)

        coVerify { repo.save(p) }
    }


}

