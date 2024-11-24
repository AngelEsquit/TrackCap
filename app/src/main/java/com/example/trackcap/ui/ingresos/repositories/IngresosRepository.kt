package com.example.trackcap.ui.ingresos.repositories

import com.example.trackcap.database.movimientos.ingresos.IngresoItemDao
import com.example.trackcap.database.movimientos.ingresos.IngresoItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IngresosRepository (private val ingresoItemDao: IngresoItemDao) {
    suspend fun getAllItems(): List<IngresoItemEntity> = withContext(Dispatchers.IO) {
        ingresoItemDao.getAllItems()
    }
}