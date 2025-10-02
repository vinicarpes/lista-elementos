package com.example.listaelementos.models

import com.example.listaelementos.database.entities.ProdutoEntity
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.repositories.paraEntidade
import com.example.listaelementos.repositories.paraProduto
import io.kotest.matchers.shouldBe
import org.junit.Test

class ProdutoTest {
    @Test
    fun `deve retornar true quando valor e quantidade for maior que zero`() {
        val produtoInvalido = Produto("Arroz", 10.0, 56, null)
        val ehValido = produtoInvalido.validaProduto()
        ehValido shouldBe true
    }

    @Test
    fun `deve retornar false quando valor ou quantidade for menor ou igual a zero`() {
        val inprodutoInvalido = Produto("Feijão", 0.0, 2, null)
        val ehValido = inprodutoInvalido.validaProduto()
        ehValido shouldBe false
    }

    @Test
    fun `deve retornar false quando nome for vazio`(){
        val inprodutoInvalido = Produto("", 10.0, 56, null)
        val ehValido = inprodutoInvalido.validaProduto()
        ehValido shouldBe false
    }

    @Test
    fun `deve retornar true quando converter entidade valida para produto`(){
        val entidadeValida = ProdutoEntity("Arroz", 10.0, 56, null)
        val produtoValido = entidadeValida.paraProduto()
        produtoValido.validaProduto() shouldBe true
    }

    @Test
    fun `deve retornar true quando converter produto valido para entidade`(){
        val produtoValido = Produto("Arroz", 10.0, 1, null)
        val entidadeValida = produtoValido.paraEntidade()
        entidadeValida.validaEntity() shouldBe true
    }

    @Test
    fun `deve retornar false quando converter produto invalido para entidade`(){
        val produtoValido = Produto("Arroz", 0.0, 1, null)
        val entidadeValida = produtoValido.paraEntidade()
        entidadeValida.validaEntity() shouldBe false
    }

    @Test
    fun `deve retornar false quando converter entidade invalida para produto`(){
        val entidadeValida = ProdutoEntity("", 10.0, 56, null)
        val produtoValido = entidadeValida.paraProduto()
        produtoValido.validaProduto() shouldBe false
    }
}