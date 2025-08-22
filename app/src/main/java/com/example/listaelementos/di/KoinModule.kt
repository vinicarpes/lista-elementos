package com.example.listaelementos.di

import androidx.room.Room
import com.example.listaelementos.database.ListaComprasDatabase
import com.example.listaelementos.repositories.ProdutoRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val storageModule = module {
    singleOf(::ProdutoRepository)
    single {
        Room.databaseBuilder(
            androidContext(),
            ListaComprasDatabase::class.java, "lista_compras.db"
        ).build()
    }
    single {
        get<ListaComprasDatabase>().produtoDao()
    }
}