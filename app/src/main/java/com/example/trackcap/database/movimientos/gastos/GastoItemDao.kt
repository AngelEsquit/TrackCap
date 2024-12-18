package com.example.trackcap.database.movimientos.gastos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GastoItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: GastoItemEntity)

    @Update
    suspend fun update(item: GastoItemEntity)

    @Delete
    suspend fun delete(item: GastoItemEntity)

    @Query("SELECT * FROM gastos_items")
    fun getAllItems(): List<GastoItemEntity>

    @Query("SELECT * FROM gastos_items WHERE date >= :date")
    fun getItemsByDate(date: Long): List<GastoItemEntity>

    @Query("SELECT * FROM gastos_items WHERE cardId = :cardId")
    fun getItemsByCardId(cardId: Int): List<GastoItemEntity>

    @Query("SELECT * FROM gastos_items WHERE category = :category")
    fun getItemsByCategory(category: String): List<GastoItemEntity>

    @Query("SELECT SUM(amount) FROM gastos_items")
    fun getTotalGastos(): Double
}