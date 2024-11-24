package com.example.trackcap

import android.app.Application
import androidx.room.Room
import com.example.trackcap.database.AppDatabase
import com.example.trackcap.ui.gastos.repositories.GastosRepository
import com.example.trackcap.ui.ingresos.repositories.IngresosRepository

class MyApp : Application() {
    private lateinit var database: AppDatabase
        private set

    lateinit var gastosRepository: GastosRepository
        private set

    lateinit var ingresosRepository: IngresosRepository
        private set

    override fun onCreate() {
        super.onCreate()

        gastosRepository = GastosRepository(
            database.gastoItemDao()
        )

        ingresosRepository = IngresosRepository(
            database.ingresoItemDao()
        )
    }
}