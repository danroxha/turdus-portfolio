package com.example.turdusportfolio.model.state

import java.math.BigDecimal

data class FinancialAsset(
    val name: String,
    val group: String,
    val averagePrice: BigDecimal,
    val amount: Long,
    val totalInvested: BigDecimal,
)
