package ru.vs.dev_helper.desktop.ui.top_bar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import ru.vs.core.decompose.view_model.decomposeViewModel

@Composable
fun TopBarScreenView() {
    val viewModel = decomposeViewModel<TopBarScreenViewModel>()
    val viewState = viewModel.viewState.collectAsState(null).value ?: return
    TopBar(viewState, viewModel::onSelectAdbDevice)
}

@Composable
private fun TopBar(
    viewState: TopBarScreenViewState,
    onClickSelectItem: (TopBarScreenViewState.AdbDeviceViewState) -> Unit
) {
    Surface(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(32.dp, 4.dp)) {
            var isDropDownMenuExpanded by remember { mutableStateOf(false) }
            Card(
                border = BorderStroke(1.dp, SolidColor(MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled))),
                elevation = 0.dp,
                modifier = Modifier.clickable { isDropDownMenuExpanded = true }
            ) {
                Text(viewState.selectedDevice?.model ?: "No Devices", Modifier.padding(8.dp, 4.dp))
            }

            SelectAdbDeviceDropdownMenu(
                isDropDownMenuExpanded,
                { isDropDownMenuExpanded = false },
                viewState.adbDevices,
                { onClickSelectItem(it); isDropDownMenuExpanded = false }
            )
        }
    }
}

@Composable
private fun SelectAdbDeviceDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    devices: List<TopBarScreenViewState.AdbDeviceViewState>,
    onClickSelectItem: (TopBarScreenViewState.AdbDeviceViewState) -> Unit,
) {
    DropdownMenu(expanded, onDismissRequest) {
        if (devices.isEmpty()) {
            DropdownMenuItem({}, enabled = false) { Text("No Devices") }
        } else {
            devices.forEach { adbDevice ->
                DropdownMenuItem({ onClickSelectItem(adbDevice) }) {
                    Text(text = adbDevice.model)
                }
            }
        }
    }
}