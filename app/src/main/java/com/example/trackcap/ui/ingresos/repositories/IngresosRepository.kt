package com.example.trackcap.ui.ingresos.repositories

import com.example.trackcap.database.movimientos.ingresos.IngresoItemDao
import com.example.trackcap.database.movimientos.ingresos.IngresoItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IngresosRepository (private val ingresoItemDao: IngresoItemDao) {
    suspend fun getAllItems(): List<IngresoItemEntity> = withContext(Dispatchers.IO) {
        ingresoItemDao.getAllItems()
    }

    suspend fun insert(item: IngresoItemEntity) = withContext(Dispatchers.IO) {
        ingresoItemDao.insert(item)
    }

    suspend fun delete(item: IngresoItemEntity) = withContext(Dispatchers.IO) {
        ingresoItemDao.delete(item)
    }

    suspend fun getItemsByDate(date: Long): List<IngresoItemEntity> = withContext(Dispatchers.IO) {
        ingresoItemDao.getItemsByDate(date)
    }

    suspend fun getItemsByCategory(category: String): List<IngresoItemEntity> = withContext(Dispatchers.IO) {
        ingresoItemDao.getItemsByCategory(category)
    }

    suspend fun getTotalIngresos(): Double = withContext(Dispatchers.IO) {
        ingresoItemDao.getTotalIngresos()
    }
}