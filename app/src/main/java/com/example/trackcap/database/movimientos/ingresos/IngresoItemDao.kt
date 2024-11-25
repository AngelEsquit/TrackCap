package com.example.trackcap.database.movimientos.ingresos

import androidx.room.Dao
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

    @Query("SELECT * FROM ingresos_items")
    fun getAllItems(): List<IngresoItemEntity>

    @Query("SELECT * FROM ingresos_items WHERE date >= :date")
    fun getItemsByDate(date: Long): List<IngresoItemEntity>
    /*
    @Query("DELETE FROM supermarket_items WHERE imagePath = :imagePath")
    suspend fun delete(imagePath: String)

    @Query("SELECT * FROM supermarket_items")
    fun getAllItems(): List<SupermarketItemEntity>

    @Query("SELECT * FROM supermarket_items WHERE itemName = :itemName AND quantity = :quantity AND imagePath = :imagePath")
    fun getItemByAtributes(itemName: String, quantity: String, imagePath: String): SupermarketItemEntity
     */
}