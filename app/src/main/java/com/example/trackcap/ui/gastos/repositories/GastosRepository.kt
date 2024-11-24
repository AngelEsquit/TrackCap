package com.example.trackcap.ui.gastos.repositories

import com.example.trackcap.database.movimientos.gastos.GastoItemDao
import com.example.trackcap.database.movimientos.gastos.GastoItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GastosRepository(private val gastoItemDao: GastoItemDao) {
    suspend fun getAllItems(): List<GastoItemEntity> = withContext(Dispatchers.IO) {
        gastoItemDao.getAllItems()
    }

    /*
    suspend fun getAllItems(): List<SupermarketItemEntity> = withContext(Dispatchers.IO) {
        supermarketItemDao.getAllItems()
    }

    suspend fun insertItem(item: SupermarketItemEntity) {
        supermarketItemDao.insert(item)
    }

    suspend fun updateItem(item: SupermarketItemEntity) {
        supermarketItemDao.update(item)
        getAllItems()
    }

    suspend fun deleteItem(path: String) {
        supermarketItemDao.delete(path)
        getAllItems()
    }

    fun getItemByAtributes(itemName: String, quantity: String, imagePath: String): SupermarketItemEntity {
        return supermarketItemDao.getItemByAtributes(itemName, quantity, imagePath)
    }

     */
}