package com.example.trackcap.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trackcap.database.invest.ActivoItemDao
import com.example.trackcap.database.invest.ActivoItemEntity
import com.example.trackcap.database.movimientos.gastos.GastoItemDao
import com.example.trackcap.database.movimientos.gastos.GastoItemEntity
import com.example.trackcap.database.movimientos.ingresos.IngresoItemDao
import com.example.trackcap.database.movimientos.ingresos.IngresoItemEntity

@Database(
    entities = [GastoItemEntity::class,
        IngresoItemEntity::class,
        ActivoItemEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gastoItemDao(): GastoItemDao
    abstract fun ingresoItemDao(): IngresoItemDao
    abstract fun activoItemDao(): ActivoItemDao
}