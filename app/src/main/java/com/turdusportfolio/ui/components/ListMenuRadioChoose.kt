package com.turdusportfolio.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.turdusportfolio.model.state.RadioChooseButtonUIState

@Composable
fun ListMenuRadioChoose(
    chooseOptionsGroup: List<RadioChooseButtonUIState>,
    onSelected: (RadioChooseButtonUIState) -> Unit = {},
) {

    LazyColumn {
        items(items = chooseOptionsGroup) { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = option.selected,
                        onClick = { onSelected(option) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = option.selected,
                    onClick = { onSelected(option) },
                    modifier = Modifier.semantics { contentDescription = option.contentDescription }
                )
                Text(text = stringResource(id = option.label))
            }
        }
    }
}