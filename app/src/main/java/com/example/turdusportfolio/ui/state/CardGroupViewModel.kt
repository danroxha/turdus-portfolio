package com.example.turdusportfolio.ui.state

import androidx.lifecycle.ViewModel
import com.example.turdusportfolio.R
import com.example.turdusportfolio.datasource.DataSource
import com.example.turdusportfolio.model.state.CardUiState
import com.example.turdusportfolio.model.state.RadioChooseButtonUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet

class CardGroupViewModel: ViewModel() {

    private val _uiState: MutableStateFlow<List<CardUiState>> = MutableStateFlow(mutableListOf())
    val uiState = _uiState.asStateFlow()

    init {
       loadCard()
    }

    fun toggleOptionPopUp(group: StateFlow<String>) {
        _uiState.update { currentState ->
            currentState.map { card ->
                card.copy(isOpen =
                    if (card.group.value == group.value)
                        MutableStateFlow(card.isOpen.updateAndGet { !it })
                    else
                        card.isOpen
                )
            }
        }
    }

    fun updateIndexerByGroup(group: String, selected: RadioChooseButtonUIState) {
        _uiState.update { currentState ->
            currentState.map { state ->
                if (state.group.value == group) {
                    state.copy(options =
                        state.options.map { option ->
                            option.copy(selected = option.label == selected.label)
                        })
                }
                else
                    state
            }
        }
    }

    private fun loadCard() {
        val groups = DataSource.financialAsserts
            .groupBy { it.group }

        val cards = groups.keys.map { group ->
            CardUiState(
                group = MutableStateFlow(group).asStateFlow(),
                options = listOf(
                    RadioChooseButtonUIState(label = R.string.financial_asset_by_name, selected = true),
                    RadioChooseButtonUIState(label = R.string.financial_asset_by_price),
                    RadioChooseButtonUIState(label = R.string.financial_asset_by_percentage_in_portfolio),
                    RadioChooseButtonUIState(label = R.string.financial_asset_by_amount),
                ),
                list = groups.getOrDefault(group, listOf())
            )
        }

        _uiState.update { cards  }
    }

}