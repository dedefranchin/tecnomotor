package com.desafio.tecnomotor.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "veiculos",
    foreignKeys = [ForeignKey(
        entity = MontadoraEntity::class,
        parentColumns = ["id"],
        childColumns = ["id_montadora"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class VeiculoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val modelo: String,
    @ColumnInfo(name = "id_montadora", index = true)
    val idMontadora: Int,
    val motorizacao: String,
    val ano: Int,
    @ColumnInfo(name = "dt_ins")
    val dtIns: Date,
    @ColumnInfo(name = "dt_upd")
    val dtUpd: Date,
    @ColumnInfo(name = "fl_ativo")
    val flAtivo: Boolean = true
) 