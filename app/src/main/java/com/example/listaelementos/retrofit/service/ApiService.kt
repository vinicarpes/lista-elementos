package com.example.listaelementos.retrofit.service

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/author")
    suspend fun ping(): Response<Unit>
}
