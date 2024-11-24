package com.example.trackcap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.trackcap.navigation.Navigation
import com.example.trackcap.ui.theme.TrackCapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrackCapTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)
            }
        }
    }
}