package com.turdusportfolio.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.RemoveCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turdusportfolio.R
import com.turdusportfolio.model.state.GoalData
import com.turdusportfolio.ui.state.GoalViewModel
import com.turdusportfolio.ui.theme.TurdusDefault
import com.turdusportfolio.ui.theme.TurdusPortfolioTheme
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Float.min
import java.math.BigDecimal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalScreen(
    previousAction: () -> Unit = {}
) {

    val goalViewModel: GoalViewModel = viewModel()
    val uiState = goalViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                modifier = Modifier
                    .height(TurdusDefault.Container.height)
                    .background(MaterialTheme.colorScheme.onPrimary),
                navigationIcon = {
                    IconButton(
                        onClick = previousAction) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.arrow_back_icon_button),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.goal_title_screen),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ },
                containerColor =  MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        },
        content = {padding ->
            LazyColumn(
                Modifier.padding(padding)
            ) {

                items(items = uiState.value) { goal ->
                    GoalItem(
                        goal = goal,
                        editAction = {},
                        deleteAction = {}
                    )
                }
            }
        }
    )
}

@Composable
fun GoalItem(
    goal: GoalData,
    deleteAction: () -> Unit = {},
    editAction: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 6.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.RemoveCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 6.dp, vertical = 6.dp)
        ) {
            Text(
                text = "1º R$ ${goal.goal.collectAsState().value}",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            val percentage by remember { mutableStateOf(goal.complete) }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                LinearProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    trackColor = MaterialTheme.colorScheme.onSecondary,
                    progress = min(1f, percentage),
                    modifier = Modifier
                        .height(TurdusDefault.Padding.middle)
                        .clip(MaterialTheme.shapes.medium)
                        .weight(2f)

                )
                Spacer(modifier = Modifier.padding(horizontal = TurdusDefault.Padding.small))
                Text(
                    text = "${goal.percentage}",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .weight(1f),
                )
            }
        }

    }
}

@Composable
@Preview(showBackground = true)
fun GoalScreenPreview1() {
    TurdusPortfolioTheme {
        Column {
            GoalItem(GoalData(current = MutableStateFlow( BigDecimal(5000.0)), position = MutableStateFlow(1), goal = MutableStateFlow(BigDecimal(100.0))))
        }
    }
}
@Composable
@Preview(showBackground = true)
fun GoalScreenPreview2() {
    TurdusPortfolioTheme {
        Column {
            GoalItem(GoalData(current = MutableStateFlow(BigDecimal(5000.0)), position = MutableStateFlow(1), goal = MutableStateFlow(BigDecimal(5_001.0))))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GoalScreenPreview3() {
    TurdusPortfolioTheme {
        Column {
            GoalItem(GoalData(current = MutableStateFlow(BigDecimal(5000.0)), position = MutableStateFlow(1), goal = MutableStateFlow(BigDecimal(100_000.0))))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun GoalScreenPreview() {
    TurdusPortfolioTheme {
        GoalScreen()
    }
}



