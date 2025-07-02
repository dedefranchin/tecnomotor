package com.desafio.tecnomotor.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.desafio.tecnomotor.data.local.dao.MontadoraDao
import com.desafio.tecnomotor.data.local.dao.VeiculoDao
import com.desafio.tecnomotor.data.local.entity.MontadoraEntity
import com.desafio.tecnomotor.data.local.entity.VeiculoEntity
import com.desafio.tecnomotor.util.DateConverter

@Database(
    entities = [MontadoraEntity::class, VeiculoEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun montadoraDao(): MontadoraDao
    abstract fun veiculoDao(): VeiculoDao
} 