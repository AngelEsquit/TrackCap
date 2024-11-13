package com.example.trackcap.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trackcap.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarTop(title: String, navController: NavController) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.clickable { /* Handle title click */ }
            ) {
                Text(text = title, color = Color.White, fontSize = 24.sp)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6650a4)),
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(35.dp),
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { navigateTo(navController, NavigationState.Home.route) }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun AppBarBottom(navController: NavController) {
    BottomAppBar(
        containerColor = Color(0xFF6650a4)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clickable { navigateTo(navController, NavigationState.Cards.route) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.credit_card),
                    contentDescription = "Cards",
                    tint = Color.White,
                    modifier = Modifier.size(35.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clickable { navigateTo(navController, NavigationState.Ingresos.route) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = "Home",
                    tint = Color.White,
                    modifier = Modifier.size(45.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clickable { navigateTo(navController, NavigationState.Gastos.route) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Home",
                    tint = Color.White,
                    modifier = Modifier.size(45.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clickable { navigateTo(navController, NavigationState.Invest.route) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.invest),
                    contentDescription = "Cards",
                    tint = Color.White,
                    modifier = Modifier.size(43.dp)
                )
            }
        }
    }
}