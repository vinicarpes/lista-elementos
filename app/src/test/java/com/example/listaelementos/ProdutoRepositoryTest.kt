package com.example.listaelementos

import com.example.listaelementos.database.dao.ProdutoDao
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.repositories.toEntity
import io.mockk.MockK
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class ProdutoRepositoryTest {

    private lateinit var dao :ProdutoDao
    private lateinit var repository : ProdutoRepository

    @Before
    fun setUp(){
        dao = mockk<ProdutoDao>()
        repository = ProdutoRepository(dao)
    }


    @Test
    fun deveChamarDaoQuandoSalvarProduto() = runTest {
        // arrange
        val produto = Produto("Arroz", 10.0, 56, null)
        val produtoEntity = produto.toEntity()

        coEvery {
            dao.insert(produtoEntity)
        }.returns(Unit)

        // act
        repository.save(produto)
        // assert
        coVerify { dao.insert(produtoEntity) }
    }

    @Test
    fun deveChamarDaoQuandoBuscarProdutos() = runTest {
        coEvery {
            dao.getAll()
        }.returns(listOf())

        repository.produtos

        coVerify {
            dao.getAll()
        }

    }
}