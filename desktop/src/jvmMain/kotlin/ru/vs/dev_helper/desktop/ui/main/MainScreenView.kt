package ru.vs.dev_helper.desktop.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import ru.vs.core.adb.data.AdbDevice
import ru.vs.core.decompose.view_model.decomposeViewModel

@Composable
fun MainScreenView() {
    val viewModel = decomposeViewModel<MainScreenViewModel>()
    val viewState = viewModel.viewState.collectAsState(null).value ?: return

    TopBar(viewState)
}

@Composable
private fun TopBar(viewState: MainScreenViewState) {
    Surface(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(32.dp, 4.dp)) {
            Card(
                border = BorderStroke(1.dp, SolidColor(MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled))),
                elevation = 0.dp,
                modifier = Modifier.clickable {  }
            ) {
                Text(
                    viewState.selectedDevice?.model ?: "<not selected>", Modifier.padding(8.dp, 4.dp),
                    style = MaterialTheme.typography.body2
                )
            }

            DropdownMenu(true, {}) {
                viewState.adbDevices.forEach { adbDevice ->
                    DropdownMenuItem({}) {
                        Text(text = adbDevice.model)
                    }
                }
            }
        }
    }
}