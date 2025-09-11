package com.example.listaelementos.di

import androidx.room.Room
import com.example.listaelementos.database.ListaComprasDatabase
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.ui.viewmodels.CadastroViewModel
import com.example.listaelementos.ui.viewmodels.MainViewModel
import com.example.listaelementos.ui.viewmodels.MainComposeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::ProdutoRepository)
    viewModelOf(::MainViewModel)
    viewModelOf(::CadastroViewModel)
    viewModelOf(::MainComposeViewModel)
    single {
        Room.databaseBuilder(
            androidContext(),
            ListaComprasDatabase::class.java, "lista_compras_database"
        ).build()
    }
    single {
        get<ListaComprasDatabase>().produtoDao()
    }
}