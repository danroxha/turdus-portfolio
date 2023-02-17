package com.example.turdusportfolio.model.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CardUiState(
    val group: StateFlow<String> = MutableStateFlow("Default"),
    val options:  List<RadioChooseButtonUIState>,
    val list: List<FinancialAsset> =  listOf(),
    val isOpen: MutableStateFlow<Boolean> = MutableStateFlow(false),
)