package com.desafio.tecnomotor.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VeiculoDto(
    val id: Int,
    val modelo: String,
    @SerializedName("id_montadora")
    val idMontadora: Int,
    val motorizacao: String,
    val ano: Int
) 