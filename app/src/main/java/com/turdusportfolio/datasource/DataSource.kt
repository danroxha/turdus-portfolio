package com.turdusportfolio.datasource

import com.turdusportfolio.model.state.FinancialAsset
import com.turdusportfolio.model.state.GoalData
import kotlinx.coroutines.flow.MutableStateFlow
import java.math.BigDecimal
import java.util.Currency
import java.util.UUID

private val FII_GROUP_ID = UUID.randomUUID()
private val ACAO_GROUP_ID = UUID.randomUUID()
private val ETF_GROUP_ID = UUID.randomUUID()
object DataSource {
    val transactions = listOf<String>()
    val financialAsserts = listOf(
        FinancialAsset(currency = Currency.getInstance("BRL"), name = "MXRF11", averagePriceDecimal = BigDecimal.valueOf(10.07), totalInvestedDecimal = BigDecimal.valueOf(553.85), amount = 55, group = "FIIs", groupId = FII_GROUP_ID),
        FinancialAsset(currency = Currency.getInstance("BRL"), name = "BCFF11", averagePriceDecimal = BigDecimal.valueOf(86.07), totalInvestedDecimal = BigDecimal.valueOf(516.42), amount = 6, group = "FIIs", groupId = FII_GROUP_ID),
        FinancialAsset(currency = Currency.getInstance("BRL"), name = "BBCS3", averagePriceDecimal = BigDecimal.valueOf(165.88), totalInvestedDecimal = BigDecimal.valueOf(323.00), amount = 2, group = "Ações", groupId = ACAO_GROUP_ID),
        FinancialAsset(currency = Currency.getInstance("BRL"), name = "IVVB11", averagePriceDecimal = BigDecimal.valueOf(165.88), totalInvestedDecimal = BigDecimal.valueOf(323.00), amount = 2, group = "ETFs", groupId = ETF_GROUP_ID),
    )

    val goals = listOf(

        GoalData(current = MutableStateFlow( BigDecimal(5000.0)), position = MutableStateFlow(1), goal = MutableStateFlow(BigDecimal(50.0))),
        GoalData(current = MutableStateFlow(BigDecimal(5000.0)), position = MutableStateFlow(1), goal = MutableStateFlow(BigDecimal(5_000.0))),
        GoalData(current = MutableStateFlow(BigDecimal(5000.0)), position = MutableStateFlow(1), goal = MutableStateFlow(BigDecimal(5_001.0))),
        GoalData(current = MutableStateFlow(BigDecimal(5000.0)), position = MutableStateFlow(1), goal = MutableStateFlow(BigDecimal(6_000.0))),
        GoalData(current = MutableStateFlow(BigDecimal(5000.0)), position = MutableStateFlow(1), goal = MutableStateFlow(BigDecimal(10_000.0))),
        GoalData(current = MutableStateFlow(BigDecimal(5000.0)), position = MutableStateFlow(1), goal = MutableStateFlow(BigDecimal(100_000.0))),
        GoalData(current = MutableStateFlow(BigDecimal(5000.0)), position = MutableStateFlow(1), goal = MutableStateFlow(BigDecimal(1_000_000.0))),
    )
}