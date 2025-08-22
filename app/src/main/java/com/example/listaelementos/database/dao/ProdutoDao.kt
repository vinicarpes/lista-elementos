package com.example.listaelementos.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.listaelementos.database.entities.ProdutoEntity

@Dao
interface ProdutoDao {
    @Query("SELECT * FROM ProdutoEntity")
    fun getAll(): List<ProdutoEntity>

    @Insert
    fun insertAll(vararg produtos: ProdutoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(produto: ProdutoEntity)

    @Delete
    fun delete(produto: ProdutoEntity)
}