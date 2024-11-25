package com.example.trackcap.ui.invest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.trackcap.database.invest.ActivoItemEntity
import com.example.trackcap.ui.invest.repository.InvestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Investment(
    val name: String,
    val originalAmount: Double,
    var currentAmount: Double
)

class InvestViewModel(private val repository: InvestRepository) : ViewModel() {
    private val _investments = MutableStateFlow<List<Investment>>(emptyList())
    val investments: StateFlow<List<Investment>> = _investments

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions

    init {
        loadInvestments()
    }

    private fun loadInvestments() {
        viewModelScope.launch {
            val investmentsFromDb = repository.getAllInvestments().map {
                Investment(it.name, it.price, 0.0)
            }
            _investments.value = investmentsFromDb
        }
    }

    fun searchInvestments(query: String) {
        repository.searchInvestments(query) { results ->
            _suggestions.value = results
        }
    }

    fun addInvestment(investment: Investment) {
        viewModelScope.launch {
            repository.insertInvestment(ActivoItemEntity(name = investment.name, price = investment.originalAmount, quantity = 1.0))
            _investments.value = _investments.value + investment
        }
    }

    fun fetchCurrentAmount(investment: Investment) {
        viewModelScope.launch {
            repository.fetchCurrentAmount(investment.name) { currentAmountInGTQ ->
                val updatedInvestments = _investments.value.map {
                    if (it.name == investment.name) it.copy(currentAmount = currentAmountInGTQ * it.originalAmount) else it
                }
                _investments.value = updatedInvestments
            }
        }
    }

}

class InvestViewModelFactory(private val repository: InvestRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InvestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InvestViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}