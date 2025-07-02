package com.desafio.tecnomotor.repository

import com.desafio.tecnomotor.data.local.dao.MontadoraDao
import com.desafio.tecnomotor.data.local.dao.VeiculoDao
import com.desafio.tecnomotor.data.local.entity.MontadoraEntity
import com.desafio.tecnomotor.data.local.entity.VeiculoDetails
import com.desafio.tecnomotor.data.local.entity.VeiculoEntity
import com.desafio.tecnomotor.data.local.entity.VeiculoWithMontadora
import com.desafio.tecnomotor.data.remote.api.ApiService
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class TecnomotorRepository @Inject constructor(
    private val montadoraDao: MontadoraDao,
    private val veiculoDao: VeiculoDao,
    private val apiService: ApiService
) {

    // Montadora methods
    fun getAllMontadoras(): Flow<List<MontadoraEntity>> = montadoraDao.getAll()

    suspend fun getMontadoraById(id: Int): MontadoraEntity? = montadoraDao.getById(id)

    suspend fun getMontadoraCount(): Int = montadoraDao.count()

    suspend fun insertMontadora(montadora: MontadoraEntity) {
        montadoraDao.insert(montadora)
    }

    suspend fun updateMontadora(montadora: MontadoraEntity) {
        montadoraDao.update(montadora)
    }

    suspend fun deleteMontadora(montadora: MontadoraEntity) {
        montadoraDao.softDelete(montadora.id, Date())
    }

    // Veiculo methods
    fun getVeiculosDetails(montadoraId: Int?, query: String?): Flow<List<VeiculoDetails>> {
        return veiculoDao.getVeiculosDetails(montadoraId, query)
    }

    suspend fun insertVeiculo(veiculo: VeiculoEntity) {
        veiculoDao.insert(veiculo)
    }

    suspend fun updateVeiculo(veiculo: VeiculoEntity) {
        veiculoDao.update(veiculo)
    }

    suspend fun deleteVeiculo(veiculo: VeiculoEntity) {
        veiculoDao.softDelete(veiculo.id, Date())
    }

    // Sync methods
    suspend fun sync() {
        val montadoras = apiService.getMontadoras().map {
            MontadoraEntity(
                id = it.id,
                nome = it.nome,
                dtIns = Date(),
                dtUpd = Date()
            )
        }
        montadoraDao.save(montadoras)

        val veiculos = apiService.getVeiculos().map {
            VeiculoEntity(
                id = it.id,
                modelo = it.modelo,
                idMontadora = it.idMontadora,
                motorizacao = it.motorizacao,
                ano = it.ano,
                dtIns = Date(),
                dtUpd = Date()
            )
        }
        veiculoDao.save(veiculos)
    }
} 