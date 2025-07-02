package com.desafio.tecnomotor.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "montadoras")
data class MontadoraEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    @ColumnInfo(name = "dt_ins")
    val dtIns: Date,
    @ColumnInfo(name = "dt_upd")
    val dtUpd: Date,
    @ColumnInfo(name = "fl_ativo")
    val flAtivo: Boolean = true
) 