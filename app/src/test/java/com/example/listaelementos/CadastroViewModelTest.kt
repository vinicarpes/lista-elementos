package com.example.listaelementos
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.listaelementos.domain.models.Produto
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
    fun deveChamarSaveDoRepoQuandoForSalvar() = runTest {
        val p = Produto("arroz", 10.0, 10, null)

        coEvery { repo.save(p) }.returns(Result.success(Unit))

        viewModel.salvar(p)

        coVerify { repo.save(p) }

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
    fun deveRetornarTrueQuandoReceberCamposNaoVaziosEmCheckFields() {
        val p = Produto("arroz", 10.0, 10, null)
        val camposPreenchidosCorretamente = viewModel.verificaCampos(p.nome, p.valor.toString(), p.quantidade.toString())

        camposPreenchidosCorretamente shouldBe true
    }

    @Test
    fun deveChamarSaveDoRepoQuandoVerificarProdutoValido() {
        val p = Produto("arroz", 10.0, 10, null)

        coEvery { repo.save(p) }.returns(Result.success(Unit))
        viewModel.valiadaParaSalvarProduct(p)

        coVerify { repo.save(p) }
    }
}

