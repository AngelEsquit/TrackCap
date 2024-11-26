package com.example.trackcap

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.trackcap.database.invest.ActivoItemEntity
import com.example.trackcap.navigation.Navigation
import com.example.trackcap.ui.cards.viewModel.Cards_ViewModel
import com.example.trackcap.ui.gastos.viewModel.GastosViewModel
import com.example.trackcap.ui.gastos.viewModel.GastosViewModelFactory
import com.example.trackcap.ui.ingresos.viewModel.IngresosViewModel
import com.example.trackcap.ui.ingresos.viewModel.IngresosViewModelFactory
import com.example.trackcap.ui.cards.viewModel.CardsViewModelFactory
import com.example.trackcap.ui.theme.TrackCapTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private lateinit var gastosViewModel: GastosViewModel
    private lateinit var ingresosViewModel: IngresosViewModel
    private lateinit var cardsViewModel: Cards_ViewModel
    private lateinit var dataSyncManager: DataSyncManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        dataSyncManager = DataSyncManager(this, this)

        val ingresosRepository = (applicationContext as MyApp).ingresosRepository
        ingresosViewModel = ViewModelProvider(
            this,
            IngresosViewModelFactory(ingresosRepository)
        )[IngresosViewModel::class.java]

        val gastosRepository = (applicationContext as MyApp).gastosRepository
        gastosViewModel = ViewModelProvider(
            this,
            GastosViewModelFactory(gastosRepository)
        )[GastosViewModel::class.java]

        val cardsRepository = (applicationContext as MyApp).cardsRepository
        cardsViewModel = ViewModelProvider(
            this,
            CardsViewModelFactory(cardsRepository)
        )[Cards_ViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            TrackCapTheme {
                val navController = rememberNavController()
                Navigation(navController = navController, gastosViewModel = gastosViewModel, ingresosViewModel = ingresosViewModel, cardsViewModel = cardsViewModel)
            }
        }

        // Inicia la sincronización al iniciar sesión
        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            if (auth.currentUser != null) {
                lifecycleScope.launch {
                    downloadDataFromFirestore("collectionName") { data ->
                        lifecycleScope.launch {
                            saveDataToRoom(this@MainActivity, data)
                            dataSyncManager.startSync()
                        }
                    }
                }
            } else {
                dataSyncManager.stopSync()
            }
        }
    }
}
