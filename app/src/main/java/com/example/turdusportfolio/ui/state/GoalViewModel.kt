package com.example.turdusportfolio.ui.state

import androidx.lifecycle.ViewModel
import com.example.turdusportfolio.datasource.DataSource
import com.example.turdusportfolio.model.state.GoalData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GoalViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<List<GoalData>> = MutableStateFlow(mutableListOf())
    val uiState: StateFlow<List<GoalData>> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        _uiState.update { DataSource.goals }
    }
}