package com.desafio.tecnomotor.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class VeiculoDetails(
    @Embedded
    val veiculo: VeiculoEntity,
    @ColumnInfo(name = "nomeMontadora")
    val nomeMontadora: String?
) 