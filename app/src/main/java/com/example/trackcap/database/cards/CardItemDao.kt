package com.example.trackcap.database.cards

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

interface CardItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CardItemEntity)

    @Update
    suspend fun update(item: CardItemEntity)

    @Query("SELECT * FROM cards_items")
    fun getAllItems(): List<CardItemEntity>
    /*
    @Query("DELETE FROM supermarket_items WHERE imagePath = :imagePath")
    suspend fun delete(imagePath: String)

    @Query("SELECT * FROM supermarket_items")
    fun getAllItems(): List<SupermarketItemEntity>

    @Query("SELECT * FROM supermarket_items WHERE itemName = :itemName AND quantity = :quantity AND imagePath = :imagePath")
    fun getItemByAtributes(itemName: String, quantity: String, imagePath: String): SupermarketItemEntity
     */
}