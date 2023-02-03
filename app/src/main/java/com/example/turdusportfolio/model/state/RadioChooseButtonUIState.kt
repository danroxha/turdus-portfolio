package com.example.turdusportfolio.model.state

data class RadioChooseButtonUIState(
    val label: String,
    val contentDescription: String = "",
    val selected: Boolean = false,
    val open: Boolean = false,
);