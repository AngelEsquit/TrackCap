package com.example.trackcap.ui.cards.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trackcap.R
import com.example.trackcap.database.cards.CardItemEntity
import com.example.trackcap.navigation.NavigationState
import com.example.trackcap.ui.cards.viewModel.Cards_ViewModel

@Composable
fun Cards (card: CardItemEntity, navController: NavController, cardsViewModel: Cards_ViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable {
                cardsViewModel.selectCard(card)
                navController.navigate(NavigationState.DetailCard.route)
            }
    ) {
        Column (
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = card.name, fontSize = 24.sp)
            Image(
                painter = painterResource(id = R.drawable.img_card),
                contentDescription = "Card icon",
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Text(text = "Monto a pagar: ${card.balance}", fontSize = 24.sp)
        }
    }
}