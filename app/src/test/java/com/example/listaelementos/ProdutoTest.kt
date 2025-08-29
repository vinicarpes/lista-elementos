package com.example.listaelementos

import com.example.listaelementos.database.entities.ProdutoEntity
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.toEntity
import com.example.listaelementos.repositories.toProduto
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProdutoTest {
    @Test
    fun deveRetornarTrueQuandoValorEQuantidadeForMaiorQueZero() {
        val validProduct = Produto("Arroz", 10.0, 56, null)
        val ehValido = validProduct.validaProduto()
        assertTrue( ehValido)
    }

    @Test
    fun deveRetornarFalseQuandoValorOuQuantidadeForMenorOuIgualAZero() {
        val invalidProduct = Produto("Feijão", 0.0, 2, null)
        val ehValido = invalidProduct.validaProduto()
        assertFalse( ehValido)
    }

    @Test
    fun deveRetornarFalseQuandoNomeForVazio(){
        val invalidProduct = Produto ("", 10.0, 56, null)
        val ehValido = invalidProduct.validaProduto()
        assertFalse( ehValido)
    }

    @Test
    fun deveRetornarTrueQuandoConverterEntidadeValidaParaProduto(){
        val validEntity = ProdutoEntity("Arroz", 10.0, 56, null)
        val validProduto = validEntity.toProduto()
        assertTrue( validProduto.validaProduto())
    }

    @Test
    fun deveRetornarTrueQuandoConverterProdutoValidoParaEntidade(){
        val validProduto = Produto("Arroz", 10.0, 1, null)
        val validEntity = validProduto.toEntity()
        assertTrue(validEntity.validaEntity())
    }

    @Test
    fun deveRetornarFalseQuandoConverterProdutoInvalidoParaEntidade(){
        val validProduto = Produto("Arroz", 0.0, 1, null)
        val validEntity = validProduto.toEntity()
        assertFalse(validEntity.validaEntity())
    }

    @Test
    fun deveRetornarFalseQuandoConverterEntidadeInvalidaParaProduto(){
        val validEntity = ProdutoEntity("", 10.0, 56, null)
        val validProduto = validEntity.toProduto()
        assertFalse( validProduto.validaProduto())
    }

}