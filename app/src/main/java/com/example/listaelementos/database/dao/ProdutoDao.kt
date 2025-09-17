package com.example.listaelementos.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.listaelementos.database.entities.ProdutoEntity
import com.example.listaelementos.domain.models.Produto

@Dao
interface ProdutoDao {
    @Query("SELECT * FROM ProdutoEntity")
    fun buscarProdutos(): List<ProdutoEntity>

    @Insert
    fun insertAll(vararg produtos: ProdutoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserir(produto: ProdutoEntity)

    @Query("DELETE FROM ProdutoEntity WHERE id = :id")
    fun deletarPorId(id: Int)

    @Update
    fun atualizar(produtoEntity: ProdutoEntity)

}