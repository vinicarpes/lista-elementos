package com.example.listaelementos

import com.example.listaelementos.database.entities.ProdutoEntity
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.toEntity
import com.example.listaelementos.repositories.toProduto
import io.kotest.matchers.shouldBe
import org.junit.Test

class ProdutoTest {
    @Test
    fun deveRetornarTrueQuandoValorEQuantidadeForMaiorQueZero() {
        val validProduct = Produto("Arroz", 10.0, 56, null)
        val ehValido = validProduct.validaProduto()
        ehValido shouldBe true
    }

    @Test
    fun deveRetornarFalseQuandoValorOuQuantidadeForMenorOuIgualAZero() {
        val invalidProduct = Produto("Feijão", 0.0, 2, null)
        val ehValido = invalidProduct.validaProduto()
        ehValido shouldBe false
    }

    @Test
    fun deveRetornarFalseQuandoNomeForVazio(){
        val invalidProduct = Produto ("", 10.0, 56, null)
        val ehValido = invalidProduct.validaProduto()
        ehValido shouldBe false
    }

    @Test
    fun deveRetornarTrueQuandoConverterEntidadeValidaParaProduto(){
        val validEntity = ProdutoEntity("Arroz", 10.0, 56, null)
        val validProduto = validEntity.toProduto()
        validProduto.validaProduto() shouldBe true
    }

    @Test
    fun deveRetornarTrueQuandoConverterProdutoValidoParaEntidade(){
        val validProduto = Produto("Arroz", 10.0, 1, null)
        val validEntity = validProduto.toEntity()
        validEntity.validaEntity() shouldBe true
    }

    @Test
    fun deveRetornarFalseQuandoConverterProdutoInvalidoParaEntidade(){
        val validProduto = Produto("Arroz", 0.0, 1, null)
        val validEntity = validProduto.toEntity()
        validEntity.validaEntity() shouldBe false
    }

    @Test
    fun deveRetornarFalseQuandoConverterEntidadeInvalidaParaProduto(){
        val validEntity = ProdutoEntity("", 10.0, 56, null)
        val validProduto = validEntity.toProduto()
        validProduto.validaProduto() shouldBe false
    }

}