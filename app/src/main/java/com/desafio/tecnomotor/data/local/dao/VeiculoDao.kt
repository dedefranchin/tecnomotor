package com.desafio.tecnomotor.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.desafio.tecnomotor.data.local.entity.VeiculoDetails
import com.desafio.tecnomotor.data.local.entity.VeiculoEntity
import com.desafio.tecnomotor.data.local.entity.VeiculoWithMontadora
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface VeiculoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(veiculos: List<VeiculoEntity>)

    @Query("""
        SELECT
            v.*,
            m.nome as nomeMontadora
        FROM veiculos v
        LEFT JOIN montadoras m ON v.id_montadora = m.id
        WHERE v.fl_ativo = 1 and m.fl_ativo = 1
          AND (:montadoraId IS NULL OR v.id_montadora = :montadoraId)
          AND (:query IS NULL OR v.modelo LIKE '%' || :query || '%')
        ORDER BY v.modelo
    """)
    fun getVeiculosDetails(montadoraId: Int?, query: String?): Flow<List<VeiculoDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(veiculo: VeiculoEntity)

    @Update
    suspend fun update(veiculo: VeiculoEntity)

    @Query("UPDATE veiculos SET fl_ativo = 0, dt_upd = :dtUpd WHERE id = :id")
    suspend fun softDelete(id: Int, dtUpd: Date)
} 