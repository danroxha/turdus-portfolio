package com.example.turdusportfolio.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.turdusportfolio.R
import com.example.turdusportfolio.model.state.CardUiState
import com.example.turdusportfolio.model.state.FinancialAsset
import com.example.turdusportfolio.model.state.RadioChooseButtonUIState
import com.example.turdusportfolio.ui.theme.TurdusPortfolioTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.NumberFormat


private val MAX_HEIGHT_CARD_FINANCE = 400.dp
private val MIN_HEIGHT_CARD_FINANCE = 0.dp

data class CardHeader(
    val options: List<RadioChooseButtonUIState>,
    val visible: StateFlow<Boolean> = MutableStateFlow(false),
    val onSelectAction: (RadioChooseButtonUIState) -> Unit,
    val onCancelAction: () -> Unit,
    val onConfirmAction: () -> Unit,
    val onSwapSortFilter: () -> Unit,
    val onToggleFilter: () -> Unit,
)

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CardFinanceAsset(
    state: CardUiState,
    header: CardHeader,
    title: StateFlow<String> = MutableStateFlow(""),
    modifier: Modifier = Modifier
) {
    Column {
        if(title.value.isNotBlank()) {
            Text(
                text = title.value,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
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
                modifier = Modifier.fillMaxWidth()
            ) {
                CardHeader(properties = header)
                CardBody(items = state.list)
                CardFooter("R$ 0,00")
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun CardHeader(
    properties: CardHeader,
) {
    val ICON_SIZE = 25.dp

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 4.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = properties.onSwapSortFilter) {
            Icon(
                imageVector = Icons.Default.SwapVert,
                contentDescription = stringResource(id = R.string.swapButtonDescription),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(ICON_SIZE)
            )
        }

        IconButton(onClick = properties.onToggleFilter) {
            Icon(
                imageVector = Icons.Outlined.FilterList,
                contentDescription = stringResource(id = R.string.filterButtonDescription),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(ICON_SIZE)
            )
        }

        AnimatedVisibility(visible = properties.visible.value) {
            CardFilterSelector(
                options = properties.options,
                onSelectAction = properties.onSelectAction,
                onConfirmAction = properties.onConfirmAction,
                onCancelAction = properties.onCancelAction,
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
            itemsIndexed(items = items) { index, item ->
                CardItemFinance(item)
                Spacer(modifier = Modifier.padding(4.dp))
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
            color = MaterialTheme.colorScheme.onPrimary,
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
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${financialAsserts.totalInvested}",
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.average_price_label, financialAsserts.totalInvested),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.amount_unit_assert_label, financialAsserts.amount),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
data class FloatDialogUiState(
    val open: Boolean = false,
)

@Composable
private fun CardFilterSelector(
    options: List<RadioChooseButtonUIState>,
    onConfirmAction: () -> Unit = {},
    onCancelAction: () -> Unit = {},
    onSelectAction: (RadioChooseButtonUIState) -> Unit = {},
    ) {

    AlertDialog(
        onDismissRequest = onCancelAction,
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
            chooseOptionsGroup = options,
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