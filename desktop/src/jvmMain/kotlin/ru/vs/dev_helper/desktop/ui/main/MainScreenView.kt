package ru.vs.dev_helper.desktop.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.kodein.di.bindProvider
import org.kodein.di.compose.subDI
import ru.vs.core.decompose.view_model.decomposeViewModel
import ru.vs.dev_helper.desktop.ui.device_settings.DeviceSettingsScreenView
import ru.vs.dev_helper.desktop.ui.top_bar.TopBarScreenView

@Composable
fun MainScreenView() {
    val viewModel = decomposeViewModel<MainScreenViewModel>()
    subDI(
        diBuilder = {
            bindProvider(overrides = true) { viewModel }
        }
    ) {
        Column {
            TopBarScreenView()
            Spacer(Modifier.height(1.dp))
            DeviceSettingsScreenView()
        }
    }
}

