package com.turdusportfolio.datasource

import com.turdusportfolio.model.state.FinancialAsset
import com.turdusportfolio.model.state.GoalData
import kotlinx.coroutines.flow.MutableStateFlow
import java.math.BigDecimal
import java.util.Currency

object DataSource {
    val transactions = listOf<String>()
    val financialAsserts = listOf(
        FinancialAsset(currency = Currency.getInstance("BRL"), name = "MXRF11", averagePriceDecimal = BigDecimal.valueOf(10.07), totalInvestedDecimal = BigDecimal.valueOf(554.95), amount = 55, group = "FIIs"),
        FinancialAsset(currency = Currency.getInstance("BRL"), name = "BBCS3", averagePriceDecimal = BigDecimal.valueOf(165.88), totalInvestedDecimal = BigDecimal.valueOf(323.00), amount = 2, group = "Ações"),
        FinancialAsset(currency = Currency.getInstance("BRL"), name = "IVVB11", averagePriceDecimal = BigDecimal.valueOf(165.88), totalInvestedDecimal = BigDecimal.valueOf(323.00), amount = 2, group = "ETFs"),
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