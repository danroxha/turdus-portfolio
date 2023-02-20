package com.turdusportfolio.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.EventRepeat
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.turdusportfolio.R
import com.turdusportfolio.ui.components.PieChartInput
import com.turdusportfolio.ui.components.PieChartSimple
import com.turdusportfolio.ui.theme.TurdusPortfolioTheme
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompoundInterestCalculatorScreen(
    previousAction: () -> Unit = {}
) {

    var initialInvestment by remember { mutableStateOf(TextFieldValue("")) }
    var continuousInvestment by remember { mutableStateOf(TextFieldValue("")) }
    var frequency by remember { mutableStateOf(TextFieldValue("")) }
    var duration by remember { mutableStateOf(TextFieldValue("")) }
    var annualInterest by remember { mutableStateOf(TextFieldValue("")) }
    var startDate by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onPrimary),
                navigationIcon = {
                    IconButton(
                        onClick = previousAction) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.arrow_back_icon_button),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.compound_interest_calculator),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
            )
        },
        floatingActionButton =  {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor =  MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ) {
                Row(
                    modifier = Modifier
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(imageVector = Icons.Default.TouchApp, contentDescription = null)
                    Text(text = "calculate")
                }
            }
        },
        content = {paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        top = paddingValues.calculateTopPadding() + 8.dp,
                        end = 8.dp
                    ),
            ){
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TextFieldAndLabel(
                            text = "Investimento Inicial",
                            placeholder = "Example: 1000.00",
                            value = initialInvestment,
                            icon = Icons.Default.MonetizationOn,
                            onValueChange = {
                                 initialInvestment = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TextFieldAndLabel(
                            text = "Investimento Continuo",
                            placeholder = "Example: 500.00",
                            value = continuousInvestment,
                            icon = Icons.Default.MonetizationOn,
                            onValueChange = {
                                continuousInvestment = it
                            },
                            modifier = Modifier
                                .weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TextFieldAndLabel(
                            text = "Frequência",
                            placeholder = " Mensal",
                            value = frequency,
                            icon = Icons.Default.EventRepeat,
                            onValueChange = {
                                 frequency = it
                            },
                            modifier = Modifier
                                .weight(1f)
                        )

                        Spacer(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                        )

                        TextFieldAndLabel(
                            text = "Duração (Ano)",
                            placeholder = "Example: 10",
                            value = duration,
                            icon = Icons.Default.DateRange,
                            onValueChange = {
                                duration = it
                            },
                            modifier = Modifier
                                .weight(1f)
                        )

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TextFieldAndLabel(
                            text = "Juros Anual",
                            placeholder = "Example: 8.0",
                            value = annualInterest,
                            onValueChange = {
                                annualInterest = it
                            },
                            icon = Icons.Default.Percent,
                            modifier = Modifier
                                .weight(1f)
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                        TextFieldAndLabel(
                            text = "Inicio",
                            placeholder = "12/30/2022",
                            value = startDate,
                            icon = Icons.Default.Today,
                            readOnly = true,
                            onValueChange = {
                                startDate = it
                            },
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                    
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                }

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                Divider(
                    color = MaterialTheme.colorScheme.onPrimary.copy(
                        alpha = MaterialTheme.colorScheme.onPrimary.alpha - 0.8f,
                    ),
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item() {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Row {
                                Icon(
                                    imageVector = Icons.Default.ManageSearch,
                                    contentDescription = stringResource(R.string.result_icon),
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                )

                                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                                Text(
                                    text = stringResource(R.string.result),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                CardResult(title = stringResource(R.string.final_total_amount), subtitle = "R$ 80.291,29", modifier = Modifier.weight(1f))

                                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                                CardResult(title = stringResource(R.string.total_amount_invested), subtitle = "R$ 65.000,00", modifier = Modifier.weight(1f))

                                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                                CardResult(title = stringResource(R.string.total_in_interest), subtitle = "R$ 15.291,29", modifier = Modifier.weight(1f))

                            }

                            PieChartSimple(
                                radius = 350f,
                                labelColor = MaterialTheme.colorScheme.onPrimary,
                                labelSize = MaterialTheme.typography.bodyLarge.fontSize,
                                input = listOf(
                                    PieChartInput(
                                        color = MaterialTheme.colorScheme.tertiary,
                                        value = 60_000_00,
                                        description = "Investimento Contínuo"
                                    ),
                                    PieChartInput(
                                        color = Color.Green,
                                        value = 30_000_00,
                                        description = "Investimento Inicial"
                                    ),
                                    PieChartInput(
                                        color = MaterialTheme.colorScheme.primary,
                                        value = 13_861_87,
                                        description = "Juros"
                                    ),
                                ),
                            )
                        }

                    }

                    item() {
                        TableComponent(
                            headerTemplate =  {
                                TableHeader()
                            },
                            rowTemplate = {
                                TableRow(it)
                            },
                            items = listOf(
                                CompoundInterestSummary(year = 2022, annualInterest = BigDecimal("290.02"), balance = BigDecimal("6790.04") ),
                                CompoundInterestSummary(year = 2023, annualInterest = BigDecimal("800.15"), balance = BigDecimal("13590.19") ),
                                CompoundInterestSummary(year = 2024, annualInterest = BigDecimal("1344.16"), balance = BigDecimal("20934.34") ),
                                CompoundInterestSummary(year = 2025, annualInterest = BigDecimal("137.91"), balance = BigDecimal("21572.25") ),
                            )
                        )
                    }
                }
            }
        }
    )
}

data class CompoundInterestSummary(
    val year: Int,
    val annualInterest: BigDecimal,
    val balance: BigDecimal
)

@Composable
fun CardResult(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults
            .cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 2.dp)
            .height(80.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "$title",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = "$subtitle",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun TableHeader() {
    Row(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .height(40.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Ano",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(0.5f)
        )
        Divider(
            color = MaterialTheme.colorScheme.onPrimary.copy(
                alpha = MaterialTheme.colorScheme.onPrimary.alpha - 0.8f,
            ),
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Text(
            text = "Juros",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
        )
        Divider(
            color = MaterialTheme.colorScheme.onPrimary.copy(
                alpha = MaterialTheme.colorScheme.onPrimary.alpha - 0.8f,
            ),
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        Text(
            text = "Saldo",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
        )
    }
}
@Composable
fun TableRow(item: CompoundInterestSummary) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth(),

    ) {
        Text(
            text = "${item.year}",
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(0.5f)
        )

        Divider(
            color = MaterialTheme.colorScheme.onPrimary.copy(
                alpha = MaterialTheme.colorScheme.onPrimary.alpha - 0.8f,
            ),
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        Text(
            text = "${item.annualInterest}",
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(end = 4.dp)
                .weight(1f)
        )

        Divider(
            color = MaterialTheme.colorScheme.onPrimary.copy(
                alpha = MaterialTheme.colorScheme.onPrimary.alpha - 0.8f,
            ),
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        Text(
            text = "${item.balance}",
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(end = 4.dp)
                .weight(1f)
        )
    }
}

@Composable
fun <T>TableComponent(
    headerTemplate: @Composable()(() -> Unit),
    rowTemplate: @Composable()((T) -> Unit),
    items: List<T>,
) {
    Column {
        headerTemplate()
        items.forEach {
            rowTemplate(it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldAndLabel(
    text: String,
    value: TextFieldValue,
    placeholder: String = "",
    onValueChange: (TextFieldValue) -> Unit,
    icon: ImageVector,
    contentDescription: String? = null,
    readOnly: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
        )
        TextField(
            readOnly = readOnly,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(
                        alpha = MaterialTheme.colorScheme.onPrimary.alpha - 0.6f,
                    ),
                )
            },
            value = value,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(imageVector = icon, contentDescription = contentDescription)
            },

            visualTransformation = VisualTransformation.None,
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.tertiary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLeadingIconColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                focusedSupportingTextColor = Color.Red,

            ),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
            ),
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .fillMaxWidth()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CompoundInterestCalculatorScreenPreview() {
    TurdusPortfolioTheme {
        CompoundInterestCalculatorScreen()
    }
}