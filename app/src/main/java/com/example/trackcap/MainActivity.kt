package com.example.trackcap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.trackcap.navigation.Navigation
import com.example.trackcap.ui.gastos.viewModel.GastosViewModel
import com.example.trackcap.ui.gastos.viewModel.GastosViewModelFactory
import com.example.trackcap.ui.ingresos.viewModel.IngresosViewModel
import com.example.trackcap.ui.ingresos.viewModel.IngresosViewModelFactory
import com.example.trackcap.ui.theme.TrackCapTheme

class MainActivity : ComponentActivity() {

    private lateinit var gastosViewModel: GastosViewModel

    private lateinit var ingresosViewModel: IngresosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        enableEdgeToEdge()
        setContent {
            TrackCapTheme {
                val navController = rememberNavController()
                Navigation(navController = navController, gastosViewModel = gastosViewModel, ingresosViewModel = ingresosViewModel)
            }
        }
    }
}