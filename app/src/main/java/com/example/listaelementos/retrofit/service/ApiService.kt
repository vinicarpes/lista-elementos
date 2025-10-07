package com.example.listaelementos.retrofit.service

import com.example.listaelementos.dto.RemoteLojasDTO
import retrofit2.http.GET

interface ApiService {
    @GET("v6.1/companies?lat=-29.587122&lng=-55.48424&from=80&sort=distance&categories=Pizza&range=15&type=Todos")
    suspend fun getLojas() : RemoteLojasDTO
}
