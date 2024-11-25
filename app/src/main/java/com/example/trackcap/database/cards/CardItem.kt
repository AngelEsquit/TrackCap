package com.example.trackcap.database.cards

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards_items")
data class CardItemEntity(
    // id autogenerated
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "paymentDate")
    val paydate: Long,

    @ColumnInfo(name = "expiryDate")
    val expiryDate: Long,

    @ColumnInfo(name = "balance")
    val balance: Double
)