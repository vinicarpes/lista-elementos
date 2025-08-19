package com.example.listaelementos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProdutoDao {
    @Query("SELECT * FROM produto")
    fun getAll(): List<Produto>

    @Insert
    fun insertAll(vararg produtos: Produto)

    @Insert
    fun insert(produto: Produto)

    @Delete
    fun delete(produto: Produto)
}