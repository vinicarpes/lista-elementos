package com.example.listaelementos.di

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.listaelementos.database.ListaComprasDatabase
import com.example.listaelementos.repositories.LojaRepository
import com.example.listaelementos.repositories.ProdutoRepository
import com.example.listaelementos.retrofit.provideOkHttpClient
import com.example.listaelementos.retrofit.provideRetrofit
import com.example.listaelementos.retrofit.service.ApiService
import com.example.listaelementos.ui.viewmodels.CadastroComposeViewModel
import com.example.listaelementos.ui.viewmodels.CadastroViewModel
import com.example.listaelementos.ui.viewmodels.ListaLojasComposeViewModel
import com.example.listaelementos.ui.viewmodels.MainViewModel
import com.example.listaelementos.ui.viewmodels.MainComposeViewModel
import com.example.listaelementos.usecases.LojaUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    singleOf(::ProdutoRepository)
    viewModelOf(::MainViewModel)
    viewModelOf(::CadastroViewModel)
    viewModelOf(::MainComposeViewModel)
    viewModelOf(::CadastroComposeViewModel)
    viewModelOf(::ListaLojasComposeViewModel)

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

val networkModule = module {
    single { provideOkHttpClient(true) }
    single { provideRetrofit(get()) }
    single < ApiService> {get<Retrofit>().create(ApiService::class.java)}
    single { LojaRepository(get()) }
    single { LojaUseCase(get()) }
}