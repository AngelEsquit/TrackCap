package com.example.trackcap

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.trackcap.database.AppDatabase
import com.example.trackcap.ui.cards.repository.CardsRepository
import com.example.trackcap.ui.gastos.repositories.GastosRepository
import com.example.trackcap.ui.ingresos.repositories.IngresosRepository
import com.example.trackcap.ui.invest.repository.InvestRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.trackcap.database.invest.ActivoItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyApp : Application() {
    lateinit var database: AppDatabase
    lateinit var ingresosRepository: IngresosRepository
    lateinit var gastosRepository: GastosRepository
    lateinit var cardsRepository: CardsRepository
    lateinit var investRepository: InvestRepository

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
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

fun uploadDataToFirestore(data: List<Any>, collectionName: String) {
    val firestore = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

    data.forEach { item ->
        firestore.collection("users").document(userId).collection(collectionName)
            .add(item)
            .addOnSuccessListener {
                // Éxito: Datos guardados correctamente
                println("Datos guardados correctamente en Firestore")
            }
            .addOnFailureListener { e ->
                // Error: Manejo de errores
                println("Error al guardar datos en Firestore: ${e.message}")
            }
    }
}
fun downloadDataFromFirestore(collectionName: String, onDataDownloaded: (List<Any>) -> Unit) {
    val firestore = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

    firestore.collection("users").document(userId).collection(collectionName)
        .get()
        .addOnSuccessListener { result ->
            val data = result.toObjects(Any::class.java)
            onDataDownloaded(data)
        }
        .addOnFailureListener { /* Manejo de errores */ }
}

class DataSyncManager(private val context: Context, private val lifecycleOwner: LifecycleOwner) {
    private val handler = Handler(Looper.getMainLooper())
    private val syncInterval =  5 * 1000L // 5 segundos

    private val syncRunnable = object : Runnable {
        override fun run() {
            lifecycleOwner.lifecycleScope.launch {
                val data = getDataFromRoom(context)
                uploadDataToFirestore(data, "collectionName")
                handler.postDelayed({ run() }, syncInterval)
            }
        }
    }

    fun startSync() {
        handler.post(syncRunnable)
    }

    fun stopSync() {
        handler.removeCallbacks(syncRunnable)
    }
}

suspend fun getDataFromRoom(context: Context): List<Any> {
    return withContext(Dispatchers.IO) {
        val database = (context.applicationContext as MyApp).database
        val investments = database.activoItemDao().getAllItems()
        investments.map { it as Any }
    }
}

suspend fun saveDataToRoom(context: Context, data: List<Any>) {
    withContext(Dispatchers.IO) {
        val database = (context.applicationContext as MyApp).database
        data.forEach { item ->
            if (item is ActivoItemEntity) {
                database.activoItemDao().insert(item)
            }
        }
    }
}