package com.turdusportfolio.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turdusportfolio.R
import com.turdusportfolio.datasource.DataSource
import com.turdusportfolio.model.state.CardUiState
import com.turdusportfolio.model.state.RadioChooseButtonUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

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
                id = groups[group]?.first()?.groupId,
                group = MutableStateFlow(group),
                options = listOf(
                    RadioChooseButtonUIState(label = R.string.financial_asset_by_name, selected = true),
                    RadioChooseButtonUIState(label = R.string.financial_asset_by_price),
                    RadioChooseButtonUIState(label = R.string.financial_asset_by_percentage_in_portfolio),
                    RadioChooseButtonUIState(label = R.string.financial_asset_by_amount),
                    RadioChooseButtonUIState(label = R.string.financial_assert_by_valuation),
                ),
                list = groups.getOrDefault(group, listOf())
            )
        }

        _uiState.update { cards }
    }

}