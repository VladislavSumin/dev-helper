package ru.vs.dev_helper.desktop.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@Composable
fun DHButton(onClick: () -> Unit, modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Card(
        border = BorderStroke(1.dp, SolidColor(MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled))),
        elevation = 0.dp,
        modifier = modifier.clickable(onClick = onClick),
    ) {
        Row(modifier.padding(8.dp, 4.dp), content = content)
    }
}