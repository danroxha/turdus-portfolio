package com.turdusportfolio.ui.state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

data class ExpandUiState(
    var groupId: UUID? = null,
    var state: Boolean = false
)

class CardGroupVisibleDetailsViewModel : ViewModel() {
    private var _uiState: MutableStateFlow<MutableMap<UUID, MutableStateFlow<ExpandUiState>>> = MutableStateFlow(mutableMapOf())

    fun registerExpandedState(id: UUID, expandedState: Boolean = false, groupId: UUID? = null): StateFlow<ExpandUiState> {

        _uiState.value.putIfAbsent(id, MutableStateFlow(ExpandUiState(state = expandedState, groupId = groupId)))

        return _uiState.value[id]!!.asStateFlow()
    }

    fun findExpandedState(id: UUID, groupId: UUID? = null): StateFlow<ExpandUiState> {
        if(!_uiState.value.containsKey(id)) {
            return registerExpandedState(id = id, groupId = groupId)
        }

        return _uiState.value[id]!!.asStateFlow()
    }

    fun toggleExpandStateFrom(id: UUID) {
        _uiState.update {
            it[id]?.update {
                it.copy(state = !it.state)
            }

            return@update it
        }
    }

    fun toggleAllExpandedStateFromGroup(groupId: UUID?) {
        if(groupId == null)
            return

        toggleExpandStateFrom(groupId)
        val groupState = findExpandedState(groupId).value

        _uiState.update { map ->
            val values = map.values
            values.forEach {state ->
                if(state.value.groupId != groupId) {
                    return@forEach
                }
                state.update {
                    it.copy(state = groupState.state)
                }
            }
            return@update map
        }
    }
}