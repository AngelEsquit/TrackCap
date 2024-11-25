package com.example.trackcap.ui.ingresos.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trackcap.database.movimientos.ingresos.IngresoItemEntity
import com.example.trackcap.ui.ingresos.repositories.IngresosRepository
import java.io.IOException

class IngresosViewModel (private val ingresosRepository: IngresosRepository): ViewModel() {

    private val _ingresos = MutableLiveData<List<IngresoItemEntity>>()
    val ingresos: LiveData<List<IngresoItemEntity>> = _ingresos

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private fun handleException(exception: Exception) {
        when (exception) {
            is IOException -> _errorMessage.value = "Network error: Check your internet connection."
            else -> _errorMessage.value = "An unexpected error occurred."
        }
        // Optionally log the exception (e.g., using a logger or crash reporting tool)
        exception.printStackTrace()
    }
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