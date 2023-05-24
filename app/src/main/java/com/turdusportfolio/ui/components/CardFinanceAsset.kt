package com.turdusportfolio.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Lens
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.UnfoldLess
import androidx.compose.material.icons.filled.UnfoldMore
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turdusportfolio.R
import com.turdusportfolio.datasource.DataSource
import com.turdusportfolio.model.state.CardUiState
import com.turdusportfolio.model.state.FinancialAsset
import com.turdusportfolio.model.state.RadioChooseButtonUIState
import com.turdusportfolio.ui.state.CardGroupVisibleDetailsViewModel
import com.turdusportfolio.ui.theme.TurdusDefault
import com.turdusportfolio.ui.theme.TurdusPortfolioTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID


private val MAX_HEIGHT_CARD_FINANCE = 500.dp
private val MIN_HEIGHT_CARD_FINANCE = 0.dp

data class CardHeaderProperties(
    val options: List<RadioChooseButtonUIState>,
    val visible: Boolean = false,
    val onSelectAction: (RadioChooseButtonUIState) -> Unit = {},
    val onCancelAction: () -> Unit = {},
    val onConfirmAction: () -> Unit = {},
    val onSwapSortFilter: () -> Unit = {},
    val onToggleFilter: () -> Unit = {},
)

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CardFinanceAsset(
    state: CardUiState,
    header: CardHeaderProperties,
    title: StateFlow<String> = MutableStateFlow(""),
    modifier: Modifier = Modifier,
    viewModel: CardGroupVisibleDetailsViewModel = viewModel(),
) {

    var localExpand = viewModel
        .findExpandedState(state.id ?: UUID.randomUUID(), groupId = state.id)
        .collectAsState()

    Column(
        modifier = modifier
    ) {
        if(title.value.isNotBlank()) {
            Text(
                text = title.value,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = TurdusDefault.Padding.large)
            )
        }
        Card(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary,
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                CardHeader(
                    properties = header,
                    expand = localExpand.value.state,
                    onExpandAction = {
                        viewModel.toggleAllExpandedStateFromGroup(state!!.id)
                    },
                )
                CardBody(items = state.list)
                CardFooter("R$ 0,00")
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun CardHeader(
    properties: CardHeaderProperties,
    expand: Boolean = false,
    onExpandAction: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(horizontal = TurdusDefault.Padding.small)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onExpandAction) {
            Icon(
                imageVector = if (expand) Icons.Default.UnfoldLess else Icons.Default.UnfoldMore,
                contentDescription = stringResource(id = R.string.unfold_button_description),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(TurdusDefault.Size.middle)
            )
        }

        IconButton(onClick = properties.onSwapSortFilter) {
            Icon(
                imageVector = Icons.Default.SwapVert,
                contentDescription = stringResource(id = R.string.swapButtonDescription),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(TurdusDefault.Size.middle)
            )
        }

        IconButton(onClick = properties.onToggleFilter) {
            Icon(
                imageVector = Icons.Outlined.FilterList,
                contentDescription = stringResource(id = R.string.filterButtonDescription),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(TurdusDefault.Size.middle)
            )
        }

        AnimatedVisibility(visible = properties.visible) {
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
private fun CardBody(
    items: List<FinancialAsset>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = MIN_HEIGHT_CARD_FINANCE, max = MAX_HEIGHT_CARD_FINANCE)
    ){
        LazyColumn {
            itemsIndexed(items = items) { index, item ->
                CardItemFinance(item = item)
                Spacer(modifier = Modifier.padding(TurdusDefault.Padding.small))
            }
        }
    }
}

@Composable
private fun CardFooter(labelFooter: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = labelFooter,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(TurdusDefault.Padding.middle)
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun CardItemFinance(
    item: FinancialAsset,
    modifier: Modifier = Modifier,
    viewModel: CardGroupVisibleDetailsViewModel = viewModel()
) {

    var localExpand = viewModel
        .findExpandedState(item.id, item.groupId)
        .collectAsState()

    val currentValuation = item.valuation;

    val valuationStatusColor =
        if (currentValuation == 0.0)
            MaterialTheme.colorScheme.onPrimary
        else if(currentValuation < 0f)
            Color.Red
        else Color.Green

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = if( currentValuation < 0f) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                contentDescription = null,
                tint = valuationStatusColor,
                modifier = Modifier
                    .width(TurdusDefault.Size.middle)
            )
            Column(
                modifier = Modifier
                    .weight(1.85f)
            ) {
                Row (
                    modifier = Modifier
                        .padding(start = TurdusDefault.Padding.large)
                ){
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "${item.totalInvested}",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(start = TurdusDefault.Padding.large)
                ) {
                    Text(
                        text = stringResource(R.string.average_price_label, item.totalInvested),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.amount_unit_assert_label, item.amount),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Row(
                modifier = Modifier
                    .weight(.15f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                IconButton(
                    onClick = {
                        viewModel.toggleExpandStateFrom(item.id)
                    },
                ) {
                    Icon(
                        imageVector = if(localExpand.value.state) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = stringResource(id = R.string.expanded_icon_button_description),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(TurdusDefault.Size.middle)
                    )
                }

            }
        }

        AnimatedVisibility(
            visible = localExpand.value.state,
            enter =  TurdusDefault.Animation.fadeInFast,
            exit = TurdusDefault.Animation.fadeOutFast
        ) {

            val currentValuation = item.valuation;

            Column(
                modifier = Modifier
                    .padding(start = TurdusDefault.Size.middle)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = TurdusDefault.Padding.large),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MarkAsListItem(
                        modifier = Modifier.weight(1.1f)
                    ) {
                        Text(
                            text = stringResource(R.string.percent_in_portfolio, item.percent),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }


                    MarkAsListItem(
                        modifier = Modifier.weight(0.9f)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.percent_in_subgroup,
                                item.groupPercentage
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = TurdusDefault.Padding.large),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MarkAsListItem(
                        modifier = Modifier.weight(1.1f)
                    ) {
                        Text(
                            text = stringResource(R.string.current_price_label, item.currentPrice),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    val valuationStatusColor =
                        if (currentValuation == 0.0)
                            MaterialTheme.colorScheme.onPrimary
                        else if(currentValuation < 0f)
                            Color.Red
                        else Color.Green

                    MarkAsListItem(
                        modifier = Modifier.weight(0.9f)
                    ) {
                        Text(
                            text = stringResource(R.string.valuation_label),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "${item.valuation}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = valuationStatusColor,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MarkAsListItem(
    modifier: Modifier = Modifier,
    content: @Composable() (() -> Unit)
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Filled.Lens,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(TurdusDefault.Size.extraSmall)
        )
        Spacer(modifier = Modifier.padding(TurdusDefault.Padding.extraSmall))
        content()
    }
}


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
        )
        },
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
        Column {
            CardFinanceAsset(
                state = CardUiState(
                    id = UUID.randomUUID(),
                    options = listOf(),
                    list = DataSource.financialAsserts,
                ),
                header = CardHeaderProperties(
                    options = listOf(),
                    visible = false
                ),
            )
        }
    }
}