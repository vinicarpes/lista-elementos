package com.example.listaelementos.repositories

import com.example.listaelementos.database.dao.ProdutoDao
import com.example.listaelementos.database.entities.ProdutoEntity
import com.example.listaelementos.domain.models.Produto
import com.example.listaelementos.utils.bitmapToByteArray
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ProdutoRepository (
    private val dao: ProdutoDao) : ILocalRepository<Produto>
 {
    val produtos get() = dao.buscarProdutos()

     override suspend fun buscarTodos() : Result<List<Produto>> = withContext(IO) {
         runCatching {
             dao.buscarProdutos().map{
                 it.paraProduto()
             }
         }
     }

    override suspend fun salvar(item: Produto) : Result<Unit> = withContext(IO) {
        runCatching {
            dao.inserir(item.paraEntidade())
        }
    }

     override suspend fun remover(item: Produto) : Result<Unit> = withContext(IO) {
         runCatching {
             dao.deletarPorId(item.id)
         }
     }
     
     override suspend fun atualizar(item: Produto, id: Int) : Result<Unit> = withContext(IO) {
         runCatching {
             val entidade = item.paraEntidade()
             entidade.id = id
             dao.atualizar(entidade)
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