package com.example.trackcap.ui.cards.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Card(
    val name: String,
    val paymentDate: String,
    val expiryDate: String,
    val spentThisMonth: Int
)

class CardsViewModel : ViewModel() {
    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> = _cards

    fun addCard(card: Card) {
        _cards.value = _cards.value + card
    }

    fun updateCard(updatedCard: Card) {
        _cards.value = _cards.value.map { if (it.name == updatedCard.name) updatedCard else it }
    }

    fun deleteCard(card: Card) {
        _cards.value = _cards.value - card
    }

    fun addExpense(card: Card, amount: Int) {
        val updatedCard = card.copy(spentThisMonth = card.spentThisMonth + amount)
        updateCard(updatedCard)
    }

    fun resetSpentAmount(card: Card) {
        val updatedCard = card.copy(spentThisMonth = 0)
        updateCard(updatedCard)
    }
}