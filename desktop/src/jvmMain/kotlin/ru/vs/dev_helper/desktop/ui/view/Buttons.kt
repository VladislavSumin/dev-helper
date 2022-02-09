package ru.vs.dev_helper.desktop.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun DHButtonDropDown(onClick: () -> Unit, modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Card(
        border = BorderStroke(1.dp, SolidColor(MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled))),
        elevation = 0.dp,
        modifier = modifier.clickable(onClick = onClick),
    ) {
        Row {
            Row(modifier.padding(8.dp, 4.dp), content = content)
            Image(
                Icons.Default.ArrowDropDown, "",
                contentScale = ContentScale.None,
                colorFilter = ColorFilter.tint(LocalContentColor.current.copy(alpha = LocalContentAlpha.current)),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(10.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}