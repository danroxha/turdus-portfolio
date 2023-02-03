package com.example.turdusportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.turdusportfolio.ui.screen.RouterApp
import com.example.turdusportfolio.ui.theme.TurdusPortfolioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TurdusPortfolioTheme {
                RouterApp()
            }
        }
    }
}

