package com.example.trackcap.database.cards

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CardItemEntity)

    @Query("SELECT * FROM cards_items")
    fun getAllItems(): List<CardItemEntity>

    @Delete
    suspend fun delete(item: CardItemEntity)

    @Update
    suspend fun update(item: CardItemEntity)

    @Query("SELECT * FROM cards_items WHERE id = :id")
    fun getItemById(id: Int): CardItemEntity

    @Query("UPDATE cards_items SET balance = :balance WHERE id = :id")
    fun updateBalance(id: Int, balance: Double)
}