package com.example.turdusportfolio.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.turdusportfolio.ui.components.CardFinanceActive
import com.example.turdusportfolio.ui.components.CardHeader
import com.example.turdusportfolio.ui.state.CardFinanceViewModel

@Composable
fun HomeScreen() {
    val cardFinanceViewModel: CardFinanceViewModel = viewModel()
    val uiState = cardFinanceViewModel.uiState.collectAsState()
    val cardGroup = cardFinanceViewModel.uiStateCardGroup

    Column {
        LazyColumn {
            items(items = cardGroup) {group ->
                CardFinanceActive(
                    state = uiState.value,
                    group = group,
                    header = CardHeader(
                        onSwapSortFilter = { },
                        onSelectAction = {
                           cardFinanceViewModel.updateSelectFilterBy(group = group, selected = it)
                        },
                        onToggleFilter = {
                            cardFinanceViewModel.toggleDialogCardFilter(group = group)
                        },
                        onCancelAction = {
                            cardFinanceViewModel.toggleDialogCardFilter(group = group)
                        },
                        onConfirmAction = {
                            cardFinanceViewModel.toggleDialogCardFilter(group = group)
                        }
                    ),
                )
            }
        }
    }
}