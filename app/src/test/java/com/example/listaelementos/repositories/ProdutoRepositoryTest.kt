package com.example.listaelementos.repositories

import com.example.listaelementos.database.dao.ProdutoDao
import com.example.listaelementos.domain.models.Produto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ProdutoRepositoryTest {

    private val dao = mockk<ProdutoDao>()
    private val repo = ProdutoRepository(dao)

    @Test
    fun `deve chamar dao quando salvar produto`() = runTest {
        val produto = Produto("Arroz", 10.0, 56, null)
        val produtoEntity = produto.paraEntidade()

        coEvery {
            dao.inserir(produtoEntity)
        }.returns(Unit)

        repo.salvar(produto)

        coVerify(exactly = 1) { dao.inserir(produtoEntity) }
    }

    @Test
    fun `deve chamar dao quando buscar produtos`() = runTest {
        coEvery {
            dao.buscarProdutos()
        }.returns(listOf())

        repo.produtos

        coVerify {
            dao.buscarProdutos()
        }

    }
}