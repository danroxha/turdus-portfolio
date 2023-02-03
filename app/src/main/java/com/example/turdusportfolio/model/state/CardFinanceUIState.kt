package com.example.turdusportfolio.model.state

data class CardFinanceUIState(
    val cardStateSelectedFilter: MutableMap<String, CardHeaderUIState> = mutableMapOf(),
    val cardFilterOpen: Boolean = false,
    val activeList: List<FinancialAsset> = listOf(),
)
