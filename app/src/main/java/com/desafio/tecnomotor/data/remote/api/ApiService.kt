package com.desafio.tecnomotor.data.remote.api

import com.desafio.tecnomotor.data.remote.dto.MontadoraDto
import com.desafio.tecnomotor.data.remote.dto.VeiculoDto
import retrofit2.http.GET

interface ApiService {

    @GET("manufactures")
    suspend fun getMontadoras(): List<MontadoraDto>

    @GET("vehicles")
    suspend fun getVeiculos(): List<VeiculoDto>

    companion object {
        const val BASE_URL = "https://processo-seletivo-653592723157.southamerica-east1.run.app/"
    }
} 