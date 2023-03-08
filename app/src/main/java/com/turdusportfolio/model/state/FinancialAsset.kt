package com.turdusportfolio.model.state

import com.turdusportfolio.ui.theme.TurdusDefault
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.UUID

private val ONE_HUNDRED_PERCENT = BigDecimal(100)

data class FinancialAsset(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val groupId: UUID,
    val group: String,
    val currency: Currency,
    val amount: Long,
    val percent: Float = 0f,
    val percentInGroup: Float = 0f,
    val groupPercentage: Float = 0f,
    private val averagePriceDecimal: BigDecimal = BigDecimal(0),
    private val currentPriceDecimal: BigDecimal = BigDecimal(0),
    private val totalInvestedDecimal: BigDecimal = BigDecimal(0),
) {
    val totalInvested: String
        get() = formatter(totalInvestedDecimal)

    val averagePrice: String
        get() = formatter(averagePriceDecimal)

    val currentPrice: Double
        get() = currentPriceDecimal
            .setScale(TurdusDefault.Format.ScaleNumber, TurdusDefault.Format.Rounding)
            .toDouble()
    val valuation: Double
        get() = currentPriceDecimal
                    .minus(averagePriceDecimal)
                    .divide(averagePriceDecimal)
                    .multiply(ONE_HUNDRED_PERCENT)
                    .toDouble()

    private fun formatter(from: BigDecimal): String {
        val formatter = NumberFormat.getCurrencyInstance();
        formatter.currency = currency
        return formatter.format(from)
    }
}
