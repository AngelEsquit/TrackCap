package com.example.trackcap.ui.invest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.trackcap.ui.invest.repository.InvestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.trackcap.networking.RetrofitClient
import com.example.trackcap.networking.response.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun fetchCurrentAmount(investment: Investment) {
        viewModelScope.launch {
            repository.fetchCurrentAmount(investment.name) { currentAmount ->
                val updatedInvestments = _investments.value.map {
                    if (it.name == investment.name) it.copy(currentAmount = currentAmount) else it
                }
                _investments.value = updatedInvestments
            }
        }
    }

    fun searchInvestments(query: String, onResult: (List<String>) -> Unit) {
        val call = RetrofitClient.instance.searchInvestments("SYMBOL_SEARCH", query, "YOUR_API_KEY")
        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    val results = response.body()?.bestMatches?.map { it.symbol } ?: emptyList()
                    onResult(results)
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                onResult(emptyList())
            }
        })
    }

    fun addInvestment(investment: Investment) {
        _investments.value = _investments.value + investment
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