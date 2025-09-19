package com.example.listaelementos.repositories

import com.example.listaelementos.database.dao.ProdutoDao
import com.example.listaelementos.database.entities.ProdutoEntity
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.utils.bitmapToByteArray
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ProdutoRepository (
    private val dao: ProdutoDao)
 {
    val produtos get() = dao.buscarProdutos()

     suspend fun buscarProdutos() : Result<List<ProdutoEntity>> = withContext(IO) {
         runCatching {
             dao.buscarProdutos()
         }
     }

    suspend fun salvar(prod: Produto) : Result<Unit> = withContext(IO) {
        runCatching {
            dao.inserir(prod.paraEntidade())
        }
    }

     suspend fun remover(produto: Produto) : Result<Unit> = withContext(IO) {
         runCatching {
             dao.deletarPorId(produto.id)
         }
     }
}

fun Produto.paraEntidade() = ProdutoEntity(
    nome = this.nome,
    valor = this.valor,
    quantidade = this.quantidade,
    foto = this.foto?.let { bitmapToByteArray(it) }
)

fun ProdutoEntity.paraProduto() = Produto(
    id = this.id,
    nome = this.nome,
    valor = this.valor,
    quantidade = this.quantidade,
    foto = null
)