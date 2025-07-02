package com.desafio.tecnomotor.data.local.entity

data class VeiculoWithMontadora(
    val id: Int,
    val modelo: String,
    val motorizacao: String,
    val ano: Int,
    val nomeMontadora: String?
) 