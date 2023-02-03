package com.example.turdusportfolio.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.turdusportfolio.R
import com.example.turdusportfolio.model.state.CardFinanceUIState
import com.example.turdusportfolio.model.state.CardHeaderUIState
import com.example.turdusportfolio.model.state.FinancialAsset
import com.example.turdusportfolio.model.state.RadioChooseButtonUIState
import com.example.turdusportfolio.ui.state.CardFinanceViewModel
import com.example.turdusportfolio.ui.theme.TurdusPortfolioTheme


private val MAX_HEIGHT_CARD_FINANCE = 400.dp
private val MIN_HEIGHT_CARD_FINANCE = 100.dp

data class CardHeader(
    val onSelectAction: (RadioChooseButtonUIState) -> Unit,
    val onCancelAction: () -> Unit,
    val onConfirmAction: () -> Unit,
    val onSwapSortFilter: () -> Unit,
    val onToggleFilter: () -> Unit,
)

@Composable
fun CardFinanceActive(
    state: CardFinanceUIState,
    group: String,
    header: CardHeader,
    suspendTitle: String = "",
    modifier: Modifier = Modifier
) {
    Column {
        if(suspendTitle.isNotBlank()) {
            Text(
                text = suspendTitle,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }
        Card(
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = modifier
                .padding(horizontal = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                CardHeader(
                    state = state,
                    group = group,
                    actions = header,
                )
                CardBody(state.activeList)
                CardFooter("R$ 0,00")
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun CardHeader(
    state: CardFinanceUIState,
    group: String,
    actions: CardHeader,
) {
    val ICON_SIZE = 30.dp


    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(4.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = actions.onSwapSortFilter) {
            Icon(
                imageVector = Icons.Default.SwapVert,
                contentDescription = stringResource(id = R.string.swapButtonDescription),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(ICON_SIZE)
            )
        }
        IconButton(onClick = actions.onConfirmAction) {
            Icon(
                imageVector = Icons.Outlined.FilterList,
                contentDescription = stringResource(id = R.string.filterButtonDescription),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(ICON_SIZE)
            )
        }
        Spacer(modifier = Modifier.padding(end = 10.dp))

        AnimatedVisibility(
            visible = state
                .cardStateSelectedFilter
                .getOrDefault(group, CardHeaderUIState(options = listOf())).open,
            enter = scaleIn() + expandVertically(),
            exit = scaleOut() + shrinkVertically()

        ) {
            CardFilterSelector(
                state = state,
                group = group,
                onSelectAction = actions.onSelectAction,
                onConfirmAction = actions.onConfirmAction,
                onCancelAction =  actions.onCancelAction,
            )
        }
    }
}

@Composable
private fun CardBody(items: List<FinancialAsset>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = MIN_HEIGHT_CARD_FINANCE, max = MAX_HEIGHT_CARD_FINANCE)
            .background(MaterialTheme.colorScheme.primary)
    ){
        LazyColumn {
            itemsIndexed(items = items) { index, value ->
                Spacer(modifier = Modifier.padding(8.dp))
                CardItemFinance(value)
                if(items.size - 1 == index) {
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
private fun CardFooter(labelFooter: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {
        Text(
            text = labelFooter,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Composable
private fun CardItemFinance(financialAsserts: FinancialAsset, modifier: Modifier = Modifier) {
    Row {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Row (
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp)
            ){
                Text(
                    text = financialAsserts.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "${financialAsserts.totalInvested}")
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp)
            ) {
                Text(stringResource(R.string.average_price_label, financialAsserts.totalInvested))
                Spacer(modifier = Modifier.weight(1f))
                Text(stringResource(R.string.amount_unit_assert_label, financialAsserts.amount))
            }
        }
    }
}
data class FloatDialogUiState(
    val open: Boolean = false,
)

@Composable
private fun CardFilterSelector(
    state: CardFinanceUIState,
    group: String,
    onConfirmAction: () -> Unit = {},
    onCancelAction: () -> Unit = {},
    onSelectAction: (RadioChooseButtonUIState) -> Unit = {},
) {
    val chooseOptions = state.cardStateSelectedFilter.getOrDefault(group, CardHeaderUIState(options = listOf()))
    Log.i("CD", "$group -> $chooseOptions -> ${state.cardStateSelectedFilter}")
    AlertDialog(
        onDismissRequest = { /*openDialog = false*/ },
        icon = {
            Icon(
                imageVector = Icons.Filled.FilterList,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        },
        title = {
            Text(text = stringResource(R.string.choose_sort_index))
        },
        text = { ListMenuRadioChoose(
            onSelected = onSelectAction,
            chooseOptionsGroup = chooseOptions.options,
        )},
        confirmButton = {
            TextButton(onClick = onConfirmAction) {
                Text(stringResource(R.string.card_filter_confirm_button))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelAction) {
                Text(stringResource(R.string.card_filter_cancel_button))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CardFinanceActivePreview() {
    TurdusPortfolioTheme {
//        Column {
//            CardFinanceActive(suspendTitle = "FIIs", items = DataSource.financialAsserts)
//            Spacer(modifier = Modifier.padding(bottom = 10.dp))
//            CardFinanceActive(suspendTitle = "Ações", items = DataSource.financialAsserts)
//        }
    }
}