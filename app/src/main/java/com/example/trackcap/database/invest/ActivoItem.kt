package com.example.trackcap.database.invest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activos_items")
data class ActivoItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "quantity")
    val quantity: Double
)