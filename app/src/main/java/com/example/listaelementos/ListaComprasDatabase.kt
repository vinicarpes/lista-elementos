package com.example.listaelementos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Produto::class], version = 1)
abstract class ListaComprasDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao
    companion object {
        private var instance: ListaComprasDatabase? = null

        @Synchronized
        fun getInstace(ctx: Context): ListaComprasDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    ListaComprasDatabase::class.java,
                    "lista_compras_database"
                ).build()
            }
            return instance!!
        }
    }
}

val Context.database: ListaComprasDatabase
    get() = ListaComprasDatabase.getInstace(applicationContext)