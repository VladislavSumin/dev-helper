package ru.vs.dev_helper.desktop.ui.device_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Checkbox
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.vs.core.decompose.view_model.decomposeViewModel

@Composable
fun DeviceSettingsScreenView() {
    val viewModel = decomposeViewModel<DeviceSettingsScreenViewModel>()
    val state = viewModel.state.collectAsState(null).value ?: return
    Surface(Modifier.fillMaxSize()) {
        when (state) {
            is DeviceSettingsScreenViewState.NoDevice -> /* no action */ Unit
            is DeviceSettingsScreenViewState.DeviceConnected -> RenderConnectedState(viewModel, state)
        }
    }
}

@Composable
private fun RenderConnectedState(
    viewModel: DeviceSettingsScreenViewModel,
    state: DeviceSettingsScreenViewState.DeviceConnected
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Show view bounds")
            Checkbox(state.isShowViewBounds, { viewModel.onSetIsShowViewBounds(it) })
        }
    }
}