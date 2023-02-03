package com.example.turdusportfolio.datasource

import com.example.turdusportfolio.model.state.FinancialAsset
import java.math.BigDecimal

object DataSource {
    val transactions = listOf<String>()
    val financialAsserts = listOf<FinancialAsset>(
        FinancialAsset(name = "MXRF11", averagePrice = BigDecimal.valueOf(10.07), totalInvested = BigDecimal.valueOf(554.95), amount = 55, group = "FII"),
        FinancialAsset(name = "BCFF11", averagePrice = BigDecimal.valueOf(70.89), totalInvested = BigDecimal.valueOf(317.35), amount = 5, group = "FII"),
        FinancialAsset(name = "HGLG11", averagePrice = BigDecimal.valueOf(165.88), totalInvested = BigDecimal.valueOf(323.00), amount = 2, group = "FII"),
        FinancialAsset(name = "HGLG11", averagePrice = BigDecimal.valueOf(165.88), totalInvested = BigDecimal.valueOf(323.00), amount = 2, group = "FII"),
        FinancialAsset(name = "HGLG11", averagePrice = BigDecimal.valueOf(165.88), totalInvested = BigDecimal.valueOf(323.00), amount = 2, group = "FII"),
        FinancialAsset(name = "BBCS3", averagePrice = BigDecimal.valueOf(165.88), totalInvested = BigDecimal.valueOf(323.00), amount = 2, group = "Ação"),
        FinancialAsset(name = "PETR3", averagePrice = BigDecimal.valueOf(165.88), totalInvested = BigDecimal.valueOf(323.00), amount = 2, group = "Ação"),
        FinancialAsset(name = "TAEE3", averagePrice = BigDecimal.valueOf(165.88), totalInvested = BigDecimal.valueOf(323.00), amount = 2, group = "Ação"),
    )
}