package com.example.trackcap.ui.ingresos.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trackcap.database.movimientos.ingresos.IngresoItemEntity
import com.example.trackcap.ui.ingresos.repositories.IngresosRepository

class IngresosViewModel (private val ingresosRepository: IngresosRepository): ViewModel() {

    private val _ingresos = MutableLiveData<List<IngresoItemEntity>>()
    val ingresos: LiveData<List<IngresoItemEntity>> = _ingresos
}

class IngresosViewModelFactory(private val repository: IngresosRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngresosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IngresosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}