package com.example.listaelementos

import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.ui.viewmodels.CadastroViewModel
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test

class CadastroViewModelTest {
    private val repo = mockk<ProdutoRepository>()
    private val viewModel = CadastroViewModel(repo)

    @Test
    fun deveChamarSaveDoRepoQuandoForSalvar() {
        val p = Produto("arroz", 10.0, 10, null)

        coEvery{ repo.save(p)}.returns(Result.success(Unit))

        viewModel.save(p)

        coVerify { repo.save(p) }
    }

    @Test
    fun deveRetornarFalsoQuandoReceberCamposVaziosEmCheckFields() {
        val p = Produto("", 1.0, 10, null)

        viewModel.checkFields(p.nome, p.valor.toString(), p.quantidade.toString()) shouldBe false
    }

    @Test
    fun deveRetornarTrueQuandoReceberCamposNaoVaziosEmCheckFields() {
        val p = Produto("arroz", 10.0, 10, null)

        viewModel.checkFields(p.nome, p.valor.toString(), p.quantidade.toString()) shouldBe true
    }

    @Test
    fun deveChamarSaveDoRepoQuandoVerificarProdutoValido() {
        val p = Produto("arroz", 10.0, 10, null)

        coEvery { repo.save(p) }.returns(Result.success(Unit))
        viewModel.valiadateToSaveProduct(p)

        coVerify { repo.save(p) }
    }


}

