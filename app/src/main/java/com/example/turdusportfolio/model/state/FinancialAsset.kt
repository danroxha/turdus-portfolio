package com.example.turdusportfolio.model.state

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import java.util.Objects

data class FinancialAsset(
    val name: String,
    val group: String,
    val currency: Currency,
    val amount: Long,
    private val averagePriceDecimal: BigDecimal = BigDecimal(0),
    private val totalInvestedDecimal: BigDecimal = BigDecimal(0),
) {
    val totalInvested: String
        get() = formatter(totalInvestedDecimal)

    val averagePrice: String
        get() = formatter(averagePriceDecimal)

    private fun formatter(from: BigDecimal): String {
        val formatter = NumberFormat.getCurrencyInstance();
        formatter.currency = currency
        return formatter.format(from)
    }
}
