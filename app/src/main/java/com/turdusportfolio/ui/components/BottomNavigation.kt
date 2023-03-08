package com.turdusportfolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.turdusportfolio.ui.theme.TurdusDefault


data class MenuItem(
    val icon: ImageVector,
    val index: Int,
    val label: String = "",
    val selected: Boolean = false,
    val perform: (NavController) -> Unit = {}
)

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    content: @Composable (RowScope.() -> Unit),
) {
    BottomAppBar(
        contentColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = modifier,
        content = {
            Row(
                content = content,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            )
        },
    )
}
@Composable
fun BottomNavigationItem(
    icon: ImageVector,
    iconSelected: ImageVector? = null,
    label: String,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val colorsSchemaSelected =
        if(selected)
            MaterialTheme.colorScheme.onSecondary
        else
            MaterialTheme.colorScheme.primary

    Row(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .width(TurdusDefault.Container.width),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = if(selected && iconSelected != null)  iconSelected else icon,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(color = colorsSchemaSelected)
                    .padding(horizontal = TurdusDefault.Padding.large, vertical = TurdusDefault.Padding.extraSmall)

            )
            if(label.isNotBlank()) {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if(selected) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1
                )
            }
        }
    }
}
