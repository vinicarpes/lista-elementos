package com.example.listaelementos.repositories

interface ILocalRepository<T> {
    suspend fun salvar(item : T) : Result<Unit>
    suspend fun buscarTodos() : Result<List<T>>
    suspend fun remover(item : T) : Result<Unit>
    suspend fun atualizar(item: T, id: Int) : Result<Unit>
}