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
import java.io.IOException

class GastosViewModel (private val gastosRepository: GastosRepository): ViewModel() {

    private val _gastos = MutableLiveData<List<GastoItemEntity>>()
    val gastos: LiveData<List<GastoItemEntity>> = _gastos

    private val _gastosByDate = MutableLiveData<List<Pair<String, Float>>>()
    val gastosByDate: LiveData<List<Pair<String, Float>>> = _gastosByDate

    private val _gastosByCardId = MutableLiveData<List<GastoItemEntity>>()
    val gastosByCardId: LiveData<List<GastoItemEntity>> = _gastosByCardId

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllItems() {
        viewModelScope.launch {
            try {
                val items = gastosRepository.getAllItems()
                _gastos.postValue(items)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun addGasto(name: String, amount: Double, category: String, date: Long, paymentMethod: String) {
        viewModelScope.launch {
            try {
                gastosRepository.insertItem(GastoItemEntity(name = name, amount = amount, category = category, date = date, paymentMethod = paymentMethod))
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun getItemsByDate(date: Long) {
        viewModelScope.launch {
            try {
                val items = gastosRepository.getItemsByDate(date)
                    .groupBy { it.category }
                    .map { Pair(it.key, it.value.sumOf { item -> item.amount }.toFloat()) }
                _gastosByDate.postValue(items)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun getItemsByCardId(cardId: Int) {
        viewModelScope.launch {
            try {
                val items = gastosRepository.getItemsByCardId(cardId)
                _gastosByCardId.postValue(items)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun handleException(exception: Exception) {
        when (exception) {
            is IOException -> _errorMessage.postValue("Network error: Check your internet connection.")
            else -> _errorMessage.postValue("An unexpected error occurred.")
        }
        exception.printStackTrace()
    }
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