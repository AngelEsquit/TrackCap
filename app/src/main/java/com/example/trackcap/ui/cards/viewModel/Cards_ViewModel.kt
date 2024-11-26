package com.example.trackcap.ui.cards.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.trackcap.database.cards.CardItemEntity
import com.example.trackcap.database.movimientos.gastos.GastoItemEntity
import com.example.trackcap.ui.cards.repository.CardsRepository
import kotlinx.coroutines.launch
import java.io.IOException

class Cards_ViewModel (private val cardsRepository: CardsRepository) : ViewModel() {
    private val _cards = MutableLiveData<List<CardItemEntity>>()
    val cards: LiveData<List<CardItemEntity>> = _cards

    private val _selectedCard = MutableLiveData<CardItemEntity?>()
    val selectedCard: LiveData<CardItemEntity?> = _selectedCard

    private val _selectedEditCard = MutableLiveData<CardItemEntity?>()
    val selectedEditCard: LiveData<CardItemEntity?> = _selectedEditCard

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun selectEditCard(card: CardItemEntity) {
        viewModelScope.launch {
            try {
                _selectedEditCard.postValue(card)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun getAllItems() {
        viewModelScope.launch {
            try {
                val items = cardsRepository.getAllItems()
                _cards.postValue(items)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun addCard(card: CardItemEntity) {
        viewModelScope.launch {
            try {
                cardsRepository.insertItem(card)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun deleteCard(card: CardItemEntity) {
        viewModelScope.launch {
            try {
                cardsRepository.deleteItem(card)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun updateCard(card: CardItemEntity) {
        viewModelScope.launch {
            try {
                cardsRepository.updateItem(card)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun selectCard(card: CardItemEntity) {
        viewModelScope.launch {
            try {
                _selectedCard.postValue(card)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun getItemById(id: Int) {
        viewModelScope.launch {
            try {
                val item = cardsRepository.getItemById(id)
                _selectedCard.postValue(item)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun updateBalance(id: Int, balance: Double) {
        viewModelScope.launch {
            try {
                cardsRepository.updateBalance(id, balance)
            } catch (e: Exception) {
                handleException(e)
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

class CardsViewModelFactory(private val repository: CardsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Cards_ViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return Cards_ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}