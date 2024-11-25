package com.example.trackcap.database.invest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ActivoItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ActivoItemEntity)

    @Update
    suspend fun update(item: ActivoItemEntity)

    @Query("SELECT * FROM activos_items")
    fun getAllItems(): List<ActivoItemEntity>
}