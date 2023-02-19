package com.turdusportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.turdusportfolio.ui.presentation.RouterApp
import com.turdusportfolio.ui.theme.TurdusPortfolioTheme

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

