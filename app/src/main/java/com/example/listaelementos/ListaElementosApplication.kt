package com.example.listaelementos

import android.app.Application
import com.example.listaelementos.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ListaElementosApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ListaElementosApplication)
            modules(appModule)
        }
    }
}