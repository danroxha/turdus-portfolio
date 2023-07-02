package com.turdusportfolio.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun BigDecimal.toCurrency(): String {
    return NumberFormat
        .getCurrencyInstance(Locale.getDefault())
        .format(this)
}


fun BigDecimal.toCurrency(currency: Currency): String {
    val format = NumberFormat
        .getCurrencyInstance(Locale.getDefault())

    format.currency = currency

    return format.format(this)
}
