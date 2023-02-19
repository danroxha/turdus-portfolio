package com.turdusportfolio.model.state

import androidx.annotation.StringRes

data class RadioChooseButtonUIState(
    @StringRes val label: Int,
    val contentDescription: String = "",
    val selected: Boolean = false,
);