package com.desafio.tecnomotor.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.desafio.tecnomotor.data.local.entity.MontadoraEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface MontadoraDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(montadoras: List<MontadoraEntity>)

    @Query("SELECT * FROM montadoras WHERE fl_ativo = 1 ORDER BY nome")
    fun getAll(): Flow<List<MontadoraEntity>>

    @Query("SELECT * FROM montadoras WHERE id = :id")
    suspend fun getById(id: Int): MontadoraEntity?

    @Query("SELECT COUNT(id) FROM montadoras")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(montadora: MontadoraEntity)

    @Update
    suspend fun update(montadora: MontadoraEntity)

    @Query("UPDATE montadoras SET fl_ativo = 0, dt_upd = :dtUpd WHERE id = :id")
    suspend fun softDelete(id: Int, dtUpd: Date)
} 