package com.example.trackcap.ui.ingresos.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.trackcap.database.movimientos.ingresos.IngresoItemEntity
import com.example.trackcap.ui.ingresos.repositories.IngresosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class IngresosViewModel(private val ingresosRepository: IngresosRepository) : ViewModel() {

    private val _ingresos = MutableLiveData<List<IngresoItemEntity>>()
    val ingresos: LiveData<List<IngresoItemEntity>> = _ingresos

    private val _ingresosCategories = MutableLiveData<List<Pair<String, Float>>>()
    val ingresosCategories: LiveData<List<Pair<String, Float>>> = _ingresosCategories

    private val _ingresosByCategory = MutableLiveData<List<IngresoItemEntity>>()
    val ingresosByCategory: LiveData<List<IngresoItemEntity>> = _ingresosByCategory

    private val _selectedCategory = MutableLiveData<String>()
    val selectedCategory: LiveData<String> = _selectedCategory

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _totalIngresos = MutableLiveData<Double>()
    val totalIngresos: LiveData<Double> = _totalIngresos

    fun calculateTotalIngresos() {
        viewModelScope.launch {
            val total = ingresosRepository.getTotalIngresos()
            _totalIngresos.postValue(total)
        }
    }

    fun getAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = ingresosRepository.getAllItems()
                _ingresos.postValue(items)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun deleteItem(item: IngresoItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ingresosRepository.delete(item)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun getItemsByDate(date: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = ingresosRepository.getItemsByDate(date)
                    .groupBy { it.category }
                    .map { Pair(it.key, it.value.sumOf { item -> item.amount }.toFloat()) }
                _ingresosCategories.postValue(items)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun getItemsByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = ingresosRepository.getItemsByCategory(category)
                _ingresosByCategory.postValue(items)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun addIngreso(name: String, amount: Double, category: String, date: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ingresosRepository.insert(IngresoItemEntity(name = name, amount = amount, category = category, date = date))
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.postValue(category)
    }

    private fun handleException(exception: Exception) {
        when (exception) {
            is IOException -> _errorMessage.postValue("Network error: Check your internet connection.")
            else -> _errorMessage.postValue("An unexpected error occurred.")
        }
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