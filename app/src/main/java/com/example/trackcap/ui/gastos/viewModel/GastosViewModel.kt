package com.example.trackcap.ui.gastos.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.trackcap.database.movimientos.gastos.GastoItemEntity
import com.example.trackcap.ui.gastos.repositories.GastosRepository
import kotlinx.coroutines.launch

class GastosViewModel (private val gastosRepository: GastosRepository): ViewModel() {

    private val _gastos = MutableLiveData<List<GastoItemEntity>>()
    val gastos: LiveData<List<GastoItemEntity>> = _gastos
}

class GastosViewModelFactory(private val repository: GastosRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GastosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GastosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}