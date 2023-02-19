package com.turdusportfolio.ui.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turdusportfolio.R
import com.turdusportfolio.ui.components.CardFinanceAsset
import com.turdusportfolio.ui.components.CardHeader
import com.turdusportfolio.ui.components.PieChart
import com.turdusportfolio.ui.components.PieChartInput
import com.turdusportfolio.ui.components.WheelTextPicker
import com.turdusportfolio.ui.state.CardGroupViewModel

@Composable
fun HomeScreen(
    navigatePieChartTarget: () -> Unit = {}
) {
    val cardGroupViewModel: CardGroupViewModel = viewModel()
    val uiState = cardGroupViewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
    ) {
        item {
            GraphComponent(
                touchPieChartTarget = navigatePieChartTarget
            )
        }
        items(items = uiState.value) {card ->
            CardFinanceAsset(
                state = card,
                title = card.group,
                header = CardHeader(
                    options = card.options,
                    visible = card.isOpen.collectAsState().value,
                    onSelectAction = { selected ->
                        cardGroupViewModel.updateIndexerByGroup(card.group.value, selected)
                        cardGroupViewModel.toggleOptionPopUp(card.group)
                    },
                    onCancelAction = {
                        cardGroupViewModel.toggleOptionPopUp(card.group)
                    },
                    onConfirmAction = {
                        cardGroupViewModel.toggleOptionPopUp(card.group)
                    },
                    onSwapSortFilter = {},
                    onToggleFilter = {
                        cardGroupViewModel.toggleOptionPopUp(card.group)
                    },
                ),
                modifier = Modifier
                    .padding(
                        bottom = 8.dp,
                    )
            )
        }
    }

}

@Composable
fun GraphComponent(
    touchPieChartFinance: () -> Unit = {},
    touchPieChartTarget: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(MaterialTheme.shapes.medium)
            .height(400.dp)
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to MaterialTheme.colorScheme.primary,
                        1f to MaterialTheme.colorScheme.secondary,
                    )
                )
            )
    ) {
        Row(
        ) {
            SelectWallet()

            Text(
                text = "Current Wallet",
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(CenterVertically)
                    .weight(1f),
                textAlign = TextAlign.Center,
            )
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Filled.Visibility,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Column(
            modifier = Modifier
                .wrapContentSize()
        ) {

            Row(modifier = Modifier.fillMaxSize()) {

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1.5f),
                ) {
                    Text(
                        text = stringResource(R.string.patrimony_label),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                    PieChart(
                        radius = 250f,
                        labelColor = MaterialTheme.colorScheme.onPrimary,
                        labelSize = MaterialTheme.typography.bodyLarge.fontSize,
                        backgroundColorCenterContent = MaterialTheme.colorScheme.primary,
                        input = listOf(
                            PieChartInput(
                                color = Color.Blue,
                                value = 29,
                                description = "FIIs"
                            ),
                            PieChartInput(
                                color = Color.Magenta,
                                value = 21,
                                description = "Ações"
                            ),
                            PieChartInput(
                                color = Color.Yellow,
                                value = 32,
                                description = "ETF"
                            ),
                            PieChartInput(
                                color = Color.Red,
                                value = 18,
                                description = "Outros"
                            ),
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShowChart,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .size(MaterialTheme.typography.displayLarge.fontSize.value.dp)
                        )
                    }

                    Text(
                        text = "R$ 5000,00",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { touchPieChartTarget() }
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.goals_label),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                    WheelTextPicker(
                        texts = listOf(" ", "1º", "2º", "3º", "4º", "5º", " "),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                    PieChart(
                        radius = 150f,
                        labelColor = MaterialTheme.colorScheme.onPrimary,
                        labelSize = MaterialTheme.typography.bodyLarge.fontSize,
                        enableSubtitle = true,
                        backgroundColorCenterContent = MaterialTheme.colorScheme.primary,
                        input = listOf(
                            PieChartInput(
                                color = MaterialTheme.colorScheme.tertiary,
                                value = 70,
                                description = stringResource(R.string.completed_label)
                            ),
                            PieChartInput(
                                color = MaterialTheme.colorScheme.primary,
                                value = 30,
                                description = stringResource(R.string.in_progress_label)
                            ),
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.EmojiEvents,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .size(MaterialTheme.typography.displaySmall.fontSize.value.dp)
                        )
                    }

                    Text(
                        text = "R$ 7000,00",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
    }
}

@Composable
fun SelectWallet() {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        IconButton(
            onClick = { },
            modifier = Modifier
                .align(CenterVertically)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Wallet,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }

    var wallets = listOf("Future 1", "Future 2",    "Future 3")

    DropdownMenu(
        expanded = false,
        onDismissRequest = {},
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        for( wallet in wallets) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = wallet,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                onClick = { /* Handle edit! */ },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Wallet,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        }
    }
}


