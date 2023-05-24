package com.turdusportfolio.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import java.util.UUID

class CardGroupVisibleDetailsViewModel : ViewModel() {
    private val uiState: MutableMap<UUID, MutableStateFlow<ExpandUiState>> = mutableMapOf()
    private val groups: MutableMap<UUID, MutableList<MutableStateFlow<ExpandUiState>>> = mutableMapOf()

    fun findExpandedState(
        id: UUID,
        groupId: UUID? = null
    ): StateFlow<ExpandUiState> {
        return uiState[id]?.asStateFlow() ?: registerExpandedState(stateId = id, groupId = groupId)
    }

    fun toggleExpandStateFrom(id: UUID): ExpandUiState? {
        var current =  uiState[id]?.updateAndGet {
            it.copy(state = !it.state)
        }

        if (isSameIds(current, id))
            return current

        viewModelScope.launch(Dispatchers.Default) {
            emitStateGlobalStateGroup(current)
        }

        return current
    }

    private fun isSameIds(
        current: ExpandUiState?,
        id: UUID
    ) = current?.groupId == id

    private fun emitStateGlobalStateGroup(current: ExpandUiState?) {
        val counter = groups[current?.groupId]?.count { it.value.state }

        val closed = 0
        val open = groups[current?.groupId]?.size

        when (counter) {
            open -> setState(current?.groupId, true)
            closed -> setState(current?.groupId, false)
        }
    }

    private fun setState(id: UUID?, state: Boolean) {
        uiState[id].let {group ->
            group?.update {
                it.copy(state = state)
            }
        }
    }

    fun toggleAllExpandedStateFromGroup(groupId: UUID?) {
        viewModelScope.launch(Dispatchers.Default) {
            if(groupId == null)
                return@launch

            val currentGlobalGroupState = toggleExpandStateFrom(groupId)

            groups[groupId]?.let { group ->
                group.forEach { state ->
                    launch(Dispatchers.Default) {
                        state.update {
                            it.copy(state = currentGlobalGroupState?.state ?: it.state )
                        }
                    }
                }
            }
        }
    }

    private fun registerExpandedState(
        stateId: UUID,
        expandedState: Boolean = false,
        groupId: UUID? = null
    ): StateFlow<ExpandUiState> {
        var currentExpandState = expandedState
        if(groupId?.equals(stateId) == false) {
            var currentGroupState = findExpandedState(id = groupId)
            currentExpandState = currentGroupState.value.state
        }

        val state = MutableStateFlow(ExpandUiState(state = currentExpandState, groupId = groupId))
        uiState.putIfAbsent(stateId, state)

        if(isInvalidOrEqualsId(groupId, stateId)) {
            return state.asStateFlow()
        }

        return appendStateOnGroup(state = state, groupId = groupId)
    }

    private fun isInvalidOrEqualsId(groupId: UUID?, stateId: UUID) =
        groupId == null || stateId == groupId

    private fun appendStateOnGroup(
        state: MutableStateFlow<ExpandUiState>,
        groupId: UUID?
    ): StateFlow<ExpandUiState> {
        if(!groups.containsKey(groupId)) {
            groupId?.let { groups.putIfAbsent(it, mutableListOf(state)) }
            return state.asStateFlow()
        }

        groups[groupId]?.let {
            it.add(state)
        }

        return state.asStateFlow()
    }
}

data class ExpandUiState(
    var groupId: UUID? = null,
    var state: Boolean = false
)