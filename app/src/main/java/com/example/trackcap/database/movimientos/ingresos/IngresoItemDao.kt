package com.example.trackcap.database.movimientos.ingresos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trackcap.database.movimientos.gastos.GastoItemEntity

@Dao
interface IngresoItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: IngresoItemEntity)

    @Update
    suspend fun update(item: IngresoItemEntity)

    @Delete
    suspend fun delete(item: IngresoItemEntity)

    @Query("SELECT * FROM ingresos_items")
    fun getAllItems(): List<IngresoItemEntity>

    @Query("SELECT * FROM ingresos_items WHERE date >= :date")
    fun getItemsByDate(date: Long): List<IngresoItemEntity>

    @Query("SELECT * FROM ingresos_items WHERE category = :category")
    fun getItemsByCategory(category: String): List<IngresoItemEntity>

    @Query("SELECT SUM(amount) FROM ingresos_items")
    fun getTotalIngresos(): Double
}