package com.example.listaelementos.repositories

interface IRemoteRepository<T> {
    suspend fun buscarDados() : T?
}