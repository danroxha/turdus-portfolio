package com.turdusportfolio.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.DataExploration
import androidx.compose.material.icons.outlined.Difference
import androidx.compose.material.icons.outlined.PriceCheck
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turdusportfolio.R
import com.turdusportfolio.ui.theme.TurdusDefault
import com.turdusportfolio.ui.theme.TurdusPaddingDefault
import com.turdusportfolio.ui.theme.TurdusPortfolioTheme
import com.turdusportfolio.ui.theme.TurdusSizeDefault

enum class ToolsRouter(val title: String) {
    CompoundInterestCalculatorScreen(title = "compound interest calculator"),
    ToolsItemsSelectorScreen(title = "tools items selector")
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsScreen() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ToolsRouter.ToolsItemsSelectorScreen.name,
        builder = {
            composable(ToolsRouter.CompoundInterestCalculatorScreen.name) {
                CompoundInterestCalculatorScreen {
                    navController.popBackStack()
                }
            }

            composable(ToolsRouter.ToolsItemsSelectorScreen.name) {
                ToolsItemsSelectorScreen(navController = navController)
            }
        }
    )
}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ToolsItemsSelectorScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                modifier = Modifier
                    .height(TurdusDefault.ContainerHeight)
                    .background(MaterialTheme.colorScheme.onPrimary),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.calculator_title_screen),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            )
        },
    ) { paddingValues ->

        val toolsCard = listOf(
            CardToolDetails(
                imageVector = Icons.Outlined.Calculate,
                contentDescription = stringResource(R.string.compound_interest_calculator_icon),
                text = stringResource(R.string.compound_interest_calculator),
                onClick = {
                    navController.navigate(route = ToolsRouter.CompoundInterestCalculatorScreen.name)
                }
            ),
            CardToolDetails(
                imageVector = Icons.Outlined.QueryStats,
                contentDescription = stringResource(R.string.benjamin_graham_calculator_icon),
                text = stringResource(R.string.benjamin_graham_calculator),
            ),
            CardToolDetails(
                imageVector = Icons.Outlined.Difference,
                contentDescription = stringResource(R.string.compare_icon),
                text = stringResource(R.string.compare),
            ),
            CardToolDetails(
                imageVector = Icons.Outlined.ReceiptLong,
                contentDescription = stringResource(R.string.report_icon),
                text = stringResource(R.string.report),
            ),
            CardToolDetails(
                imageVector = Icons.Outlined.DataExploration,
                contentDescription = stringResource(R.string.benchmarking_icon),
                text = stringResource(R.string.benchmarking),
            ),
            CardToolDetails(
                imageVector = Icons.Outlined.PriceCheck,
                contentDescription = stringResource(R.string.dividends_icon),
                text = stringResource(R.string.dividends),
            ),
            CardToolDetails(
                imageVector = Icons.Outlined.TrackChanges,
                contentDescription = stringResource(R.string.goals_icon),
                text = stringResource(R.string.goals),
            )
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(TurdusPaddingDefault.middlePadding),
            verticalArrangement = Arrangement.spacedBy(TurdusPaddingDefault.middlePadding),
            contentPadding = PaddingValues(TurdusPaddingDefault.middlePadding)
        ) {

            itemsIndexed(items = toolsCard) {index, details ->
                CardTool(
                    details = details,
                )
            }
        }
    }
}

data class CardToolDetails(
    val imageVector: ImageVector,
    val contentDescription: String,
    val text: String,
    val onClick: () -> Unit = {}
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardTool(
    details: CardToolDetails,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier
            .size(TurdusSizeDefault.extraLargeSize),
        onClick = details.onClick,
        shape = MaterialTheme.shapes.medium,
    ) {
       Column(
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center,
           modifier = Modifier
               .fillMaxSize()
               .background(MaterialTheme.colorScheme.primary)
               .padding(TurdusPaddingDefault.middlePadding)
       ) {
           Icon(
               imageVector = details.imageVector,
               contentDescription = details.contentDescription,
               tint = MaterialTheme.colorScheme.onPrimary,
               modifier = Modifier
                   .size(MaterialTheme.typography.displayMedium.fontSize.value.dp)
           )
           Text(
               text = details.text,
               textAlign = TextAlign.Center,
               style = MaterialTheme.typography.titleSmall,
               color = MaterialTheme.colorScheme.onPrimary,
           )
       }
}
}

@Composable
@Preview(showBackground = true)
fun CardToolPreview() {
    TurdusPortfolioTheme {
        CardTool(
           details = CardToolDetails(
               imageVector = Icons.Outlined.QueryStats,
               contentDescription = "benjamin graham calculator icon",
               text = "Benjamin Graham calculator",
           )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ToolsScreenPreview() {
    TurdusPortfolioTheme {
        ToolsScreen()
    }
}