package com.turdusportfolio.model.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.UUID

data class GoalData(
    val uuid: StateFlow<UUID> = MutableStateFlow(UUID.randomUUID()).asStateFlow(),
    val goal: StateFlow<BigDecimal>,
    val current: StateFlow<BigDecimal>,
    val position: StateFlow<Long>,
) {
    val complete: Float
        get() = (current.value.toDouble() * 100f / goal.value.toDouble() / 100f)
            .toBigDecimal()
            .min(BigDecimal(1.00))
            .setScale(4, RoundingMode.HALF_EVEN)
            .toFloat()

    val percentage: String
        get() = "${complete
            .toBigDecimal()
            .multiply(BigDecimal(100.00))
            .setScale(2, RoundingMode.HALF_EVEN)
            .toPlainString()}%"

}