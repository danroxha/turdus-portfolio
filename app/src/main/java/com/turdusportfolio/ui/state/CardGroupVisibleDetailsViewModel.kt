package com.turdusportfolio.ui.state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import java.util.UUID

data class ExpandUiState(
    var groupId: UUID? = null,
    var state: Boolean = false
)

class CardGroupVisibleDetailsViewModel : ViewModel() {
    private val uiState: MutableMap<UUID, MutableStateFlow<ExpandUiState>> = mutableMapOf()
    private val groups: MutableMap<UUID, MutableList<MutableStateFlow<ExpandUiState>>> = mutableMapOf()

    fun findExpandedState(
        id: UUID,
        groupId: UUID? = null
    ): StateFlow<ExpandUiState> {
        return uiState
            .getOrDefault(
                key = id,
                defaultValue = registerExpandedState(stateId = id, groupId = groupId)
            )
    }

    fun toggleExpandStateFrom(id: UUID): ExpandUiState? {
        return uiState[id]?.updateAndGet {
            it.copy(state = !it.state)
        }
    }

    fun toggleAllExpandedStateFromGroup(groupId: UUID?) {
        if(groupId == null)
            return

        val currentGlobalGroupState = toggleExpandStateFrom(groupId)

        groups[groupId]?.let { group ->
            group.forEach { state ->
                state.update {
                    it.copy(state = currentGlobalGroupState?.state ?: it.state )
                }
            }
        }
    }

    private fun registerExpandedState(
        stateId: UUID,
        expandedState: Boolean = false,
        groupId: UUID? = null
    ): StateFlow<ExpandUiState> {
        val state = MutableStateFlow(ExpandUiState(state = expandedState, groupId = groupId))
        uiState.putIfAbsent(stateId, state)

        if(groupId == null) {
            return state.asStateFlow()
        }

        return appendStateOnGroup(state = state, groupId = groupId)
    }

    private fun appendStateOnGroup(
        state: MutableStateFlow<ExpandUiState>,
        groupId: UUID
    ): StateFlow<ExpandUiState> {
        if(!groups.containsKey(groupId)) {
            groups.putIfAbsent(groupId, mutableListOf(state))
            return state.asStateFlow()
        }

        groups[groupId]?.let {
            it.add(state)
        }

        return state.asStateFlow()
    }
}