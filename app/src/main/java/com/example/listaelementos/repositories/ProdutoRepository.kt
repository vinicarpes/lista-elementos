package com.example.listaelementos.repositories

import com.example.listaelementos.utils.bitmapToByteArray
import com.example.listaelementos.database.dao.ProdutoDao
import com.example.listaelementos.database.entities.ProdutoEntity
import com.example.listaelementos.domain.models.Produto
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ProdutoRepository (
    private val dao: ProdutoDao)
 {
    val produtos get() = dao.getAll()

    suspend fun save(prod: Produto) = withContext(IO) {
        dao.insert(prod.toEntity())
    }

}

fun Produto.toEntity() = ProdutoEntity(
    nome = this.nome,
    valor = this.valor,
    quantidade = this.quantidade,
    foto = this.foto?.let { bitmapToByteArray(it) }
)

fun ProdutoEntity.toProduto() = Produto(
    id = this.id,
    nome = this.nome,
    valor = this.valor,
    quantidade = this.quantidade,
    foto = null
)