package com.turdusportfolio.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun BigDecimal.toCurrency(): String {
    return NumberFormat
        .getCurrencyInstance(Locale.getDefault())
        .format(this)
}
