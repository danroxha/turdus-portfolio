package com.turdusportfolio.model.state

import com.turdusportfolio.ui.state.ExpandUiState
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

data class CardUiState(
    val id: UUID?,
    val expanded: MutableStateFlow<ExpandUiState> =  MutableStateFlow(ExpandUiState()),
    val group: MutableStateFlow<String> = MutableStateFlow("Default"),
    val options:  List<RadioChooseButtonUIState>,
    val list: List<FinancialAsset> =  listOf(),
    val isOpen: MutableStateFlow<Boolean> = MutableStateFlow(false),
)