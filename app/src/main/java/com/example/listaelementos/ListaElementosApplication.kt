package com.example.listaelementos

import android.app.Application
import com.example.listaelementos.di.appModule
import com.example.listaelementos.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ListaElementosApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ListaElementosApplication)
            modules(appModule, networkModule)
        }
    }
}