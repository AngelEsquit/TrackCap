package com.example.trackcap.ui.cards.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trackcap.R
import com.example.trackcap.navigation.AppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun cardsScreen(navController: NavController) {
    val cards = listOf(
        Triple("Tarjeta 1", "21/10/2024", 100),
        Triple("Tarjeta 2", "13/12/2024", 200),
        Triple("Tarjeta 3", "15/02/2025", 150),
    )
    val color = colorScheme.tertiaryContainer

    val img = R.drawable.img_card

    Scaffold (topBar = { AppBar(title = "Tarjetas", navController) }
    ) { innerPadding ->
        LazyColumn (
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            item {
                Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = color
                    )) {
                    Text(text = "Agregar tarjeta",
                        color = Color.Black)
                }
            }

            item {
                cards.forEach { card ->
                    Card (modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)){
                        Row (modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)){
                            Image(painter = painterResource(id = img), contentDescription = null)

                            Column (modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp)) {
                                Text(text = card.first)
                                Text(text = "Vence: ${card.second}")
                                Text(text = "Saldo: Q ${card.third}")
                            }
                        }
                    }
                }
            }
        }
    }
}