package com.example.trackcap.ui.gastos.repositories

import com.example.trackcap.database.movimientos.gastos.GastoItemDao
import com.example.trackcap.database.movimientos.gastos.GastoItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GastosRepository(private val gastoItemDao: GastoItemDao) {
    suspend fun getAllItems(): List<GastoItemEntity> = withContext(Dispatchers.IO) {
        gastoItemDao.getAllItems()
    }

    suspend fun insertItem(item: GastoItemEntity) {
        gastoItemDao.insert(item)
    }

    suspend fun deleteItem(item: GastoItemEntity) {
        gastoItemDao.delete(item)
    }

    suspend fun getItemsByDate(date: Long): List<GastoItemEntity> = withContext(Dispatchers.IO) {
        gastoItemDao.getItemsByDate(date)
    }

    suspend fun getItemsByCardId(cardId: Int): List<GastoItemEntity> = withContext(Dispatchers.IO) {
        gastoItemDao.getItemsByCardId(cardId)
    }

    suspend fun getTotalGastos(): Double = withContext(Dispatchers.IO) {
        gastoItemDao.getTotalGastos()
    }

    suspend fun getGastosByCategory(category: String): List<GastoItemEntity> = withContext(Dispatchers.IO) {
        gastoItemDao.getItemsByCategory(category)
    }
}