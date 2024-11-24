package com.example.trackcap.database.invest

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

interface ActivoItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ActivoItemEntity)

    @Update
    suspend fun update(item: ActivoItemEntity)

    @Query("SELECT * FROM activos_items")
    fun getAllItems(): List<ActivoItemEntity>
    /*
    @Query("DELETE FROM supermarket_items WHERE imagePath = :imagePath")
    suspend fun delete(imagePath: String)

    @Query("SELECT * FROM supermarket_items")
    fun getAllItems(): List<SupermarketItemEntity>

    @Query("SELECT * FROM supermarket_items WHERE itemName = :itemName AND quantity = :quantity AND imagePath = :imagePath")
    fun getItemByAtributes(itemName: String, quantity: String, imagePath: String): SupermarketItemEntity
     */
}