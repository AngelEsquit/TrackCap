package com.example.trackcap.ui.cards.repository

import com.example.trackcap.database.cards.CardItemDao
import com.example.trackcap.database.cards.CardItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CardsRepository (private val cardItemDao: CardItemDao) {
    suspend fun getAllItems(): List<CardItemEntity> = withContext(Dispatchers.IO) {
        cardItemDao.getAllItems()
    }

    suspend fun insertItem(item: CardItemEntity) {
        cardItemDao.insert(item)
    }

    suspend fun deleteItem(item: CardItemEntity) {
        cardItemDao.delete(item)
    }

    suspend fun updateItem(item: CardItemEntity) {
        cardItemDao.update(item)
    }

    suspend fun getItemById(id: Int): CardItemEntity {
        return cardItemDao.getItemById(id)
    }

    suspend fun updateBalance(id: Int, balance: Double) {
        cardItemDao.updateBalance(id, balance)
    }
}