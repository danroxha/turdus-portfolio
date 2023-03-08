package com.turdusportfolio.ui.theme

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.ui.unit.dp
import java.math.RoundingMode

object TurdusDefault {
    object Container {
        val largeHeight = 80.0.dp
        val height = 60.0.dp
        val width = 60.dp
        val smallHeight = 40.dp
        val smallWidth = 40.dp
    }

    object Padding {
        val extraLarge = 30.dp
        val large = 10.dp
        val middle = 8.0.dp
        val small = 4.dp
        val extraSmall = 2.dp
    }

    object Duration {
        val fastest = 100
        val normal = 300
        val slowest = 500
        val laziest = 1000
    }

    object Size {
        val extraLarge = 150.dp
        val middle = 25.dp
        val small = 12.dp
        val extraSmall = 6.dp
    }
    object Format {
        val Rounding = RoundingMode.HALF_EVEN
        val ScaleNumber = 2
    }

    object Animation {
        val fadeInFast = fadeIn(animationSpec = tween(Duration.fastest)) + expandVertically(animationSpec = tween(Duration.fastest))
        val fadeOutFast = fadeOut(animationSpec = tween(Duration.fastest)) + shrinkVertically(animationSpec = tween(Duration.fastest))
    }
}
