package com.example.turdusportfolio.ui.state

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.turdusportfolio.datasource.DataSource
import com.example.turdusportfolio.model.state.CardFinanceUIState
import com.example.turdusportfolio.model.state.CardHeaderUIState
import com.example.turdusportfolio.model.state.FinancialAsset
import com.example.turdusportfolio.model.state.RadioChooseButtonUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update

class CardFinanceViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CardFinanceUIState(activeList = DataSource.financialAsserts))
    private val _uiStateCards =  MutableStateFlow(_uiState.value.activeList.groupBy { it.group }).asStateFlow()
    var uiState: StateFlow<CardFinanceUIState> = _uiState.asStateFlow()
    val uiStateCardGroup: List<String> = _uiStateCards.value.keys.toList()

    init {

        val options: MutableMap<String, CardHeaderUIState> = mutableMapOf()

        uiStateCardGroup.forEach {
            options.put(
                it, CardHeaderUIState(options = listOf(
                    RadioChooseButtonUIState(label = "nome do ativo"),
                    RadioChooseButtonUIState(label = "preço do ativo"),
                    RadioChooseButtonUIState(label = "% na carteira"),
                    RadioChooseButtonUIState(label = "quantidade"),
                ))
            )
        }


        _uiState.update { currentState ->
            currentState.copy(
                activeList = DataSource.financialAsserts,
                cardStateSelectedFilter = options,
            )
        }
    }

    fun changeFilterSelected(cardKey: String, radioChoose: RadioChooseButtonUIState) {
        val card = getCardByKey(cardKey)
        val options = card
            .options
            .map { it -> it.copy(selected = it.label == radioChoose.label) }

        val map = _uiState.getAndUpdate { it }.cardStateSelectedFilter

        map.replace(cardKey, card.copy(options = options))

        _uiState.update { currentState ->
            currentState.copy(cardStateSelectedFilter = map)
        }
    }

    private fun getCardByKey(cardKey: String): CardHeaderUIState {
       return uiState.value.cardStateSelectedFilter.getOrDefault(cardKey, CardHeaderUIState(options = listOf()))
    }

    fun updateSelectFilterBy(group: String, selected: RadioChooseButtonUIState) {
        val currentUiOptions = uiState.value.cardStateSelectedFilter

        val groupOption = currentUiOptions.getOrDefault(group, CardHeaderUIState(options = listOf()))

        val options = groupOption.options
            .map {
                it.copy(selected = if (selected.label == it.label) selected.selected else false)
            }

        currentUiOptions.replace(group, CardHeaderUIState(options = options))
        _uiState.update { currentState ->
            currentState.copy(
                cardStateSelectedFilter = currentUiOptions
            )
        }
    }

    fun toggleDialogCardFilter(group: String) {
        val currentUiOptions = uiState.value.cardStateSelectedFilter

        var groupOption = currentUiOptions.getOrDefault(group, CardHeaderUIState(options = listOf()))


        currentUiOptions.replace(group, groupOption.copy(open = !groupOption.open))
        Log.i("TOOGLE", "$group - ${ currentUiOptions }")

        _uiState.update {currentState ->
            currentState.copy(
                cardStateSelectedFilter = currentUiOptions,
            )
        }


    }
}