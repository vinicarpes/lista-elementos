package com.example.listaelementos

import com.example.listaelementos.database.dao.ProdutoDao
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.repositories.toEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test


class ProdutoRepositoryTest {
    @Test
    fun deveChamarDaoQuandoSalvarProduto() = runTest {
        // arrange
        val dao = mockk<ProdutoDao>()
        val repository = ProdutoRepository(dao)
        val produto = Produto("Arroz", 10.0, 56, null)
        val produtoEntity = produto.toEntity()

        coEvery  {
            dao.insert(produtoEntity)
        }.returns(Unit)

        // act
        repository.save(produto)
        // assert
        coVerify { dao.insert(produtoEntity) }
    }

}