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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trackcap.R
import com.example.trackcap.database.cards.CardItemEntity

@Composable
fun Cards (card: CardItemEntity, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable { navController.navigate("card/${card.id}") }
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.img_card),
                contentDescription = "Card icon",
                modifier = Modifier.padding(16.dp)
            )
            Column {
                Text(text = card.name)
            }
        }
    }
}