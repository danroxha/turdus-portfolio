package com.turdusportfolio.model.state

import kotlinx.coroutines.flow.MutableStateFlow

data class CardUiState(
    val group: MutableStateFlow<String> = MutableStateFlow("Default"),
    val options:  List<RadioChooseButtonUIState>,
    val list: List<FinancialAsset> =  listOf(),
    val isOpen: MutableStateFlow<Boolean> = MutableStateFlow(false),
)