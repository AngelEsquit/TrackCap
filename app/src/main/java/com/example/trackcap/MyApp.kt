package com.example.trackcap

import android.app.Application
import androidx.room.Room
import com.example.trackcap.database.AppDatabase
import com.example.trackcap.ui.cards.repository.CardsRepository
import com.example.trackcap.ui.gastos.repositories.GastosRepository
import com.example.trackcap.ui.ingresos.repositories.IngresosRepository
import com.example.trackcap.ui.invest.repository.InvestRepository


class MyApp : Application() {
    lateinit var database: AppDatabase
    lateinit var ingresosRepository: IngresosRepository
    lateinit var gastosRepository: GastosRepository
    lateinit var cardsRepository: CardsRepository
    lateinit var investRepository: InvestRepository

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "trackcap-database"
        ).build()

        ingresosRepository = IngresosRepository(database.ingresoItemDao())
        gastosRepository = GastosRepository(database.gastoItemDao())
        cardsRepository = CardsRepository(database.cardItemDao())
        investRepository = InvestRepository(database.activoItemDao())
    }
}